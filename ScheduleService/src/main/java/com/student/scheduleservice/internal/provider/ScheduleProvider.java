package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.PriceListDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.dal.UnavailabilityDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.soap.contract.scheduleservice.SoapAddCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
import com.student.soap.contract.scheduleservice.SoapCarAvailabilityRequest;
import com.student.soap.contract.scheduleservice.SoapCarAvailabilityResponse;
import com.student.soap.contract.scheduleservice.SoapCarPhysicalRequest;
import com.student.soap.contract.scheduleservice.SoapCarPhysicalResponse;
import com.student.soap.contract.scheduleservice.SoapDeleteCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;


@Component("ScheduleProvider")
public class ScheduleProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;

	@Autowired
	public ScheduleProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
	}

	public InternalCarRatingResponse getCarRating(int id) {
		InternalCarRatingResponse response = new InternalCarRatingResponse();
		int sum = 0;
		int count = 0;
		List<ReservationDbModel> dbResponses = unitOfWork.getReservationRepo().findByCarId(id);
		for (ReservationDbModel objectIn : dbResponses) {
			if (objectIn.getRating() != null) {
				sum += objectIn.getRating();
				count++;
			}
		}
		response.setRating(Math.round((float) sum / (float) count));
		response.setSuccess(true);
		return response;
	}

	public InternalCarPriceResponse getCarPrice(InternalCarPriceRequest request) {
		InternalCarPriceResponse response = new InternalCarPriceResponse();
		CarPriceListDbModel carPricelist = unitOfWork.getCarPriceListRepo().findByCarId(request.getCarId()).stream()
				.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		if (carPricelist == null) {
			return response;
		}

		//TODO: check for unavailabilities
		
		//TODO: check fot existing reservations
		
		List<PriceDbModel> prices = carPricelist.getPriceList().getPrices().stream()
				.filter(price -> price.getStartDate().compareTo(request.getStartDate()) >= 0
						&& price.getEndDate().compareTo(request.getEndDate()) >= 0)
				.collect(Collectors.toList());

		int price = 0;
		for (PriceDbModel dailyPrice : prices) {
			price += dailyPrice.getPrice();
		}

		int discount = 0;
		response.setCollisionWarranty(carPricelist.getPriceList().getWarrantyPrice());
		if (request.getStartDate().compareTo(request.getEndDate()) >= 30) {
			discount = price * carPricelist.getPriceList().getDiscountPercentage() / 100;
		}
		response.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
		response.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
		response.setPrice(price);
		response.setTotalPrice(price - discount);
		response.setDiscount(discount);
		response.setSuccess(true);
		return response;
	}

	public SoapCarAvailabilityResponse getCarAvailability(SoapCarAvailabilityRequest request) {
		SoapCarAvailabilityResponse response = new SoapCarAvailabilityResponse();

		// posmatraj aktivan cenovnik
		CarPriceListDbModel carPriceList = unitOfWork.getCarPriceListRepo().findByCarId(request.getId()).stream()
				.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		List<PriceDbModel> prices = carPriceList.getPriceList().getPrices().stream().filter(
				price -> price.getStartDate().compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& price.getEndDate().compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0)
				.collect(Collectors.toList());

		if (prices.isEmpty()) {
			response.setSuccess(false);
			return response;
		}

		// proveri da li se vozilo nalazi u unavailability tabeli
		if (unitOfWork.getUnavailabilityRepo().findByCarId(request.getId()) != null) {
			response.setSuccess(false);
			return response;
		}
		// treca provera sa rezervacijama
		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(request.getId()).stream()
				.filter(reservation -> reservation.getStartDate()
						.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& reservation.getStartDate()
								.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0
						|| reservation.getStartDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) <= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0
						|| reservation.getEndDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0)
				.collect(Collectors.toList());

		if (!reservations.isEmpty()) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}

	public SoapCarPhysicalResponse getCarPhysical(SoapCarPhysicalRequest request) {
		// TODO Auto-generated method stub
		SoapCarPhysicalResponse response = new SoapCarPhysicalResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!jwtUtil.isAuthorized(token, 3, request.getPublisherId(), request.getPublisherTypeId())) {
			response.setAuthorized(false);
			return response;
		}

		// response.setAuthorized(true);

		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(request.getCarId()).stream()
				.filter(reservation -> reservation.getStartDate()
						.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& reservation.getStartDate()
								.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0
						|| reservation.getStartDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) <= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0
						|| reservation.getEndDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0)
				.collect(Collectors.toList());

		for (ReservationDbModel reservation : reservations) {
			response.setTest(reservation.getBundle().getState().getName());
			if (reservation.getBundle().getState().getName().equals("RESERVATION_PAID")) {
				response.setSuccess(false);
				return response;
			}
		}
		// u protivnom, ako ne postoji preklapanje sa rezervacijama koje su vec placene, slobodan je da
		// nastavi
		UnavailabilityDbModel unavaible = new UnavailabilityDbModel();
		unavaible.setCarId(request.getCarId());
		unavaible.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		unavaible.setEndDate(request.getEndDate().toGregorianCalendar().getTime());
		unitOfWork.getUnavailabilityRepo().save(unavaible);
		response.setSuccess(true);
		return response;
	}

	public SoapResponse addCarPriceList(SoapAddCarPriceListRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		
		if (!jwtUtil.isAuthorized(token, 4, request.getPublisherId(), request.getPublisherTypeId())) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);

		CarPriceListDbModel carPriceList = new CarPriceListDbModel();
		
		Optional<PriceListDbModel> model = unitOfWork.getPriceListRepo().findById(request.getPriceListId());
		
		carPriceList.setCarId(request.getCarId());
		carPriceList.setPriceList(model.get());
		
		unitOfWork.getCarPriceListRepo().save(carPriceList);
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deleteCarPriceList(SoapDeleteCarPriceListRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<CarPriceListDbModel> carPriceList = unitOfWork.getCarPriceListRepo().findById(request.getId());
		if(!carPriceList.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		
		try {
			unitOfWork.getCarPriceListRepo().delete(carPriceList.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse addPriceList(SoapAddPriceListRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
	
		if (!jwtUtil.isAuthorized(token, 4, request.getPublisherId(), request.getPublisherTypeId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		PriceListDbModel priceList = new PriceListDbModel();
		priceList.setDiscountPercentage(request.getDiscountPercentage());
		priceList.setMileagePenalty(request.getMileagePenalty());
		priceList.setMileageThreshold(request.getMileageThreshold());
		priceList.setName(request.getName());
		priceList.setWarrantyPrice(request.getWarrantyPrice());
		priceList.setPublisherId(request.getPublisherId());
		priceList.setPublisherType(unitOfWork.getPublisherTypeRepo().findById(request.getPublisherTypeId()).get());
		
		try {
			unitOfWork.getPriceListRepo().save(priceList);
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deletePriceList(SoapDeletePriceListRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional <PriceListDbModel> priceList = unitOfWork.getPriceListRepo().findById(request.getId());
		if(!priceList.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		try {
			unitOfWork.getPriceListRepo().delete(priceList.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		response.setSuccess(true);
		return response;
	}

	public SoapResponse addPrice(SoapAddPriceRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
	
		if (!jwtUtil.isAuthorized(token, 4, request.getPublisherId(), request.getPublisherTypeId())) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);
		
		PriceDbModel price = new PriceDbModel();
		price.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		price.setEndDate(request.getEndDate().toGregorianCalendar().getTime());
		price.setPrice(request.getPrice());
		price.setPriceList(unitOfWork.getPriceListRepo().findById(request.getPriceListId()).get());
		
		unitOfWork.getPriceRepo().save(price);
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deletePrice(SoapDeletePriceRequest request) {
		// TODO Auto-generated method stub
		SoapResponse response = new SoapResponse();
		
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());
		if (!token.isValid() || !jwtUtil.isAdmin(token)) {
			response.setAuthorized(false);
			return response;
		}
		
		Optional<PriceDbModel> price = unitOfWork.getPriceRepo().findById(request.getId());
		if(!price.isPresent()) {
			response.setSuccess(false);
			return response;
		}
		try {
			unitOfWork.getPriceRepo().delete(price.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		response.setSuccess(true);
		return response;
	}
}
