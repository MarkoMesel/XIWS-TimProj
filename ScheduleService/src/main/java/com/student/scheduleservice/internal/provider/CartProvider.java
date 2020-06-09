package com.student.scheduleservice.internal.provider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.student.scheduleservice.soap.contract.SoapCartAddCarRequest;
import com.student.scheduleservice.soap.contract.SoapResponse;
import com.student.soap.carService.contract.SoapCarRequest;
import com.student.soap.carService.contract.SoapCarResponse;

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
}
