package com.student.scheduleservice.internal.provider;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.PublisherTypeDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.soap.client.AgentServiceClient;
import com.student.scheduleservice.soap.client.CarServiceClient;
import com.student.scheduleservice.soap.client.UserServiceClient;
import com.student.scheduleservice.soap.contract.Bundle;
import com.student.scheduleservice.soap.contract.Car;
import com.student.scheduleservice.soap.contract.SoapCartAddCarRequest;
import com.student.scheduleservice.soap.contract.SoapCartRequest;
import com.student.scheduleservice.soap.contract.SoapCartResponse;
import com.student.scheduleservice.soap.contract.SoapResponse;
import com.student.soap.carservice.contract.SoapCarRequest;
import com.student.soap.carservice.contract.SoapCarResponse;

@Component("CartProvider")
public class CartProvider {

	private UnitOfWork unitOfWork;
	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;
	private CarServiceClient carServiceClient;
	private ScheduleProvider scheduleProvider;
	private JwtUtil jwtUtil;

	@Autowired
	public CartProvider(UnitOfWork unitOfWork, UserServiceClient userServiceClient,
			AgentServiceClient agentServiceClient, JwtUtil jwtUtil, ScheduleProvider scheduleProvider,
			CarServiceClient carServiceClient) {
		super();
		this.unitOfWork = unitOfWork;
		this.userServiceClient = userServiceClient;
		this.agentServiceClient = agentServiceClient;
		this.carServiceClient = carServiceClient;
		this.jwtUtil = jwtUtil;
		this.scheduleProvider = scheduleProvider;
	}

	public SoapResponse addCarToCart(SoapCartAddCarRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		// fetch car
		SoapCarRequest soapCarRequest = new SoapCarRequest();
		soapCarRequest.setId(request.getCarId());
		soapCarRequest.setStartDate(request.getStartDate());
		soapCarRequest.setEndDate(request.getEndDate());
		SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);

		if (!soapCarResponse.isSuccess()) {
			return response;
		}
		
		//check for existing bundle
		BundleDbModel bundle = unitOfWork.getBundleRepo()
				.findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(token.getUserId(), 7, soapCarResponse.getCar().getPublisherId(), soapCarResponse.getCar().getPublisherTypeId());

		//check if the car is already in cart
		//TODO: allow same car at different date? Not MVP
		if(bundle!= null) {
			int existingCarInCart = bundle.getReservations().stream().filter(reservation->reservation.getCarId()==request.getCarId()).collect(Collectors.toList()).size();
			if( existingCarInCart>0) {
				return response;				
			}
		}
		
		if (bundle == null || bundle.getReservations().size() <= 1) {
			bundle = new BundleDbModel();
			bundle.setUserId(token.getUserId());
			bundle.setState(unitOfWork.getReservationStateRepo().findById(7).get());

			bundle.setPublisherId(soapCarResponse.getCar().getPublisherId());
			Optional<PublisherTypeDbModel> publisherType = unitOfWork.getPublisherTypeRepo()
					.findById(soapCarResponse.getCar().getPublisherTypeId());
			if (publisherType.isPresent()) {
				bundle.setPublisherType(publisherType.get());
			}
			unitOfWork.getBundleRepo().save(bundle);
		}

		ReservationDbModel reservation = new ReservationDbModel();
		reservation.setBundle(bundle);
		reservation.setCarId(request.getCarId());
		reservation.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		reservation.setEndDate(request.getEndDate().toGregorianCalendar().getTime());
		
		if(request.isCollisionWarranty()) {
			reservation.setTotalPrice(soapCarResponse.getCar().getTotalPrice() + soapCarResponse.getCar().getCollisionWaranty());			
		}
		else {
			reservation.setTotalPrice(soapCarResponse.getCar().getTotalPrice());
		}
		
		unitOfWork.getReservationRepo().save(reservation);
		response.setSuccess(true);
		return response;
	}
	
	public SoapCartResponse getCart(SoapCartRequest request) {
		SoapCartResponse response = new SoapCartResponse();
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), 7);
		for(BundleDbModel bundleIn: bundles)
		{
			Bundle bundleOut = new Bundle();
			bundleOut.setBundleId(bundleIn.getId());
			bundleOut.setPublisherId(bundleIn.getPublisherId());
			bundleOut.setPublisherTypeId(bundleIn.getPublisherType().getId());
			bundleOut.setPublisherTypeName(bundleIn.getPublisherType().getName());
			
			//Fetch publisher name
			bundleOut.setPublisherName(scheduleProvider.fetchPublisherName(bundleIn.getPublisherType().getName(), bundleIn.getPublisherId()));
			
			for(ReservationDbModel reservationIn: bundleIn.getReservations()) {				
				try {
					Car reservationOut = new Car();
					
					// fetch car
					SoapCarRequest soapCarRequest = new SoapCarRequest();
					soapCarRequest.setId(reservationIn.getCarId());
					GregorianCalendar startDateGreg = new GregorianCalendar();
					startDateGreg.setTime(reservationIn.getStartDate());
					GregorianCalendar endDateGreg = new GregorianCalendar();
					endDateGreg.setTime(reservationIn.getEndDate());
					soapCarRequest.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateGreg));
					soapCarRequest.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateGreg));
					SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);

					if (!soapCarResponse.isSuccess()) {
						return response;
					}
					
					reservationOut.setReservationId(reservationIn.getId());
					reservationOut.setCarId(reservationIn.getCarId());
					reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
					reservationOut.setTotalPrice(reservationIn.getTotalPrice());
					
					reservationOut.setMileagePenalty(soapCarResponse.getCar().getMileagePenalty());
					reservationOut.setMileageThreshold(soapCarResponse.getCar().getMileageThreshold());
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
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
			}
			
			response.getBundle().add(bundleOut);
		}
		
		response.setSuccess(true);
		return response;
	}
}
