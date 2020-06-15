package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.dal.ReservationStateDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.soap.client.AgentServiceClient;
import com.student.scheduleservice.soap.client.CarServiceClient;
import com.student.scheduleservice.soap.client.UserServiceClient;
import com.student.soap.contract.agentservice.SoapAgentByIdResponse;
import com.student.soap.contract.carservice.SoapCarRequest;
import com.student.soap.contract.carservice.SoapCarResponse;
import com.student.soap.contract.scheduleservice.Bundle;
import com.student.soap.contract.scheduleservice.Car;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.userservice.SoapGetResponse;

@Component("ProviderUtil")
public class ProviderUtil {

	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;
	private CarServiceClient carServiceClient;
	private UnitOfWork unitOfWork;

	@Autowired
	public ProviderUtil(UserServiceClient userServiceClient, AgentServiceClient agentServiceClient,
			UnitOfWork unitOfWork, CarServiceClient carServiceClient) {
		super();
		this.userServiceClient = userServiceClient;
		this.agentServiceClient = agentServiceClient;
		this.unitOfWork = unitOfWork;
		this.carServiceClient = carServiceClient;
	}

	public ReservationStateDbModel getCartState() {
		return unitOfWork.getReservationStateRepo().findById(1).get();
	}

	public ReservationStateDbModel getPendingState() {
		return unitOfWork.getReservationStateRepo().findById(2).get();
	}

	public ReservationStateDbModel getCanceledState() {
		return unitOfWork.getReservationStateRepo().findById(3).get();
	}

	public ReservationStateDbModel getPaidState() {
		return unitOfWork.getReservationStateRepo().findById(4).get();
	}

	public ReservationStateDbModel getRejectedState() {
		return unitOfWork.getReservationStateRepo().findById(5).get();
	}

	public ReservationStateDbModel getCompletedState() {
		return unitOfWork.getReservationStateRepo().findById(6).get();
	}

	public String fetchPublisherName(String publisherTypeName, Integer publisherId) {
		// Fetch publisher name
		// If user
		if (publisherTypeName.equals("USER")) {
			try {
				SoapGetResponse userResponse = userServiceClient.getUser(publisherId);
				if (userResponse.isSuccess()) {
					return userResponse.getFirstName() + " " + userResponse.getLastName();
				}
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}

		// If agent
		if (publisherTypeName.equals("AGENT")) {
			try {
				SoapAgentByIdResponse agentResponse = agentServiceClient.getAgent(publisherId);
				if (agentResponse.isSuccess()) {
					return agentResponse.getName();
				}
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		return null;
	}

	public XMLGregorianCalendar getXmlGregorianCalendar(Long timestamp) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public XMLGregorianCalendar getXmlGregorianCalendar(Date date) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SoapBundlesResponse getSoapBundles(List<BundleDbModel> input) {
		SoapBundlesResponse output = new SoapBundlesResponse();

		for (BundleDbModel bundleIn : input) {
			Bundle bundleOut = new Bundle();
			bundleOut.setBundleId(bundleIn.getId());
			bundleOut.setPublisherId(bundleIn.getPublisherId());
			bundleOut.setPublisherTypeId(bundleIn.getPublisherType().getId());
			bundleOut.setPublisherTypeName(bundleIn.getPublisherType().getName());
			bundleOut.setUserId(bundleIn.getUserId());
			bundleOut.setUserName(fetchPublisherName("USER", bundleIn.getUserId()));
			bundleOut.setStateId(bundleIn.getState().getId());
			bundleOut.setStateName(bundleIn.getState().getName());
			
			// Fetch publisher name
			bundleOut.setPublisherName(
					fetchPublisherName(bundleIn.getPublisherType().getName(), bundleIn.getPublisherId()));

			for (ReservationDbModel reservationIn : bundleIn.getReservations()) {
				Car reservationOut = new Car();

				// fetch car
				SoapCarRequest soapCarRequest = new SoapCarRequest();
				soapCarRequest.setId(reservationIn.getCarId());

				SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);

				if (!soapCarResponse.isSuccess()) {
					return output;
				}

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut.setExtraCharges(reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = unitOfWork.getCarPriceListRepo().findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
						.orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}
				
				reservationOut.setCarClassId(soapCarResponse.getCar().getCarClassId());
				reservationOut.setCarClassName(soapCarResponse.getCar().getCarClassName());
				reservationOut.setLocationId(soapCarResponse.getCar().getLocationId());
				reservationOut.setLocationName(soapCarResponse.getCar().getLocationName());
				reservationOut.setModelId(soapCarResponse.getCar().getModelId());
				reservationOut.setModelName(soapCarResponse.getCar().getModelName());
				reservationOut.setManufacturerId(soapCarResponse.getCar().getManufacturerId());
				reservationOut.setManufacturerName(soapCarResponse.getCar().getManufacturerName());
				reservationOut.setFuelTypeName(soapCarResponse.getCar().getFuelTypeName());
				reservationOut.setFuelTypeId(soapCarResponse.getCar().getFuelTypeId());
				reservationOut.setTransmissionTypeName(soapCarResponse.getCar().getTransmissionTypeName());
				reservationOut.setTransmissionTypeId(soapCarResponse.getCar().getTransmissionTypeId());
				reservationOut.setMileage(soapCarResponse.getCar().getMileage());
				reservationOut.setChildSeats(soapCarResponse.getCar().getChildSeats());
				reservationOut.setPublisherId(soapCarResponse.getCar().getPublisherId());
				reservationOut.setPublisherTypeId(soapCarResponse.getCar().getPublisherTypeId());
				reservationOut.setPublisherTypeName(soapCarResponse.getCar().getPublisherTypeName());
				reservationOut.setRating(soapCarResponse.getCar().getRating());
				reservationOut.getImage().addAll(soapCarResponse.getCar().getImage());

				bundleOut.getCar().add(reservationOut);
			}

			output.getBundle().add(bundleOut);
		}

		output.setAuthorized(true);
		output.setSuccess(true);
		return output;
	}
}
