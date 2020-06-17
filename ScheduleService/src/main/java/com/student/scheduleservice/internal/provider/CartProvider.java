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
import com.student.scheduleservice.soap.client.CarServiceClient;
import com.student.soap.contract.carservice.SoapCarRequest;
import com.student.soap.contract.carservice.SoapCarResponse;
import com.student.soap.contract.scheduleservice.SoapBundlesResponse;
import com.student.soap.contract.scheduleservice.SoapCartAddCarRequest;
import com.student.soap.contract.scheduleservice.SoapCartBundleRequest;
import com.student.soap.contract.scheduleservice.SoapCartRemoveCarRequest;
import com.student.soap.contract.scheduleservice.SoapCartRequest;
import com.student.soap.contract.scheduleservice.SoapCartUnbundleRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("CartProvider")
public class CartProvider {

	private UnitOfWork unitOfWork;
	private CarServiceClient carServiceClient;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public CartProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil,
			CarServiceClient carServiceClient, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.carServiceClient = carServiceClient;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
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

		// check for existing bundle
		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(
				token.getUserId(), providerUtil.getCartState().getId(), soapCarResponse.getCar().getPublisherId(),
				soapCarResponse.getCar().getPublisherTypeId());
		BundleDbModel bundle = null;
		// check if the car is already in cart
		// TODO: allow same car at different date? Not MVP
		if (bundles.size() == 1) {
			bundle = bundles.get(0);
			int existingCarInCart = bundle.getReservations().stream()
					.filter(reservation -> reservation.getCarId() == request.getCarId()).collect(Collectors.toList())
					.size();
			if (existingCarInCart > 0) {
				return response;
			}
		}

		if (bundle == null || bundle.getReservations().size() <= 1) {
			bundle = new BundleDbModel();
			bundle.setUserId(token.getUserId());
			bundle.setState(providerUtil.getCartState());

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

		if (request.isCollisionWarranty()) {
			reservation.setTotalPrice(
					soapCarResponse.getCar().getTotalPrice() + soapCarResponse.getCar().getCollisionWaranty());
		} else {
			reservation.setTotalPrice(soapCarResponse.getCar().getTotalPrice());
		}

		unitOfWork.getReservationRepo().save(reservation);
		response.setSuccess(true);
		return response;
	}

	public SoapBundlesResponse getCart(SoapCartRequest request) {
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			SoapBundlesResponse response = new SoapBundlesResponse();
			response.setAuthorized(false);
			return response;
		}

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), providerUtil.getCartState().getId());
		
		SoapBundlesResponse response = providerUtil.getSoapBundles(bundles);
		
		return response;
	}

	public SoapResponse removeCarFromCart(SoapCartRemoveCarRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		Optional<ReservationDbModel> car = unitOfWork.getReservationRepo().findById(request.getReservationId());
		if (!car.isPresent() || car.get().getBundle().getState().getId() != providerUtil.getCartState().getId()
				|| car.get().getBundle().getUserId() != token.getUserId()) {
			return response;
		}

		BundleDbModel bundle = car.get().getBundle();

		if (bundle.getReservations().size() == 1) {
			unitOfWork.getBundleRepo().delete(bundle);
		} else {
			// need to fetch the reservation through the bundle, else exception
			ReservationDbModel reservation = bundle.getReservations().stream()
					.filter(x -> x.getId() == request.getReservationId()).findFirst().get();
			unitOfWork.getReservationRepo().delete(reservation);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapResponse bundle(SoapCartBundleRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(
				token.getUserId(), providerUtil.getCartState().getId(), request.getPublisherId(), request.getPublisherTypeId());
		if (bundles.size() <= 1) {
			return response;
		}

		BundleDbModel bundle = new BundleDbModel();
		bundle.setPublisherId(request.getPublisherId());
		bundle.setPublisherType(unitOfWork.getPublisherTypeRepo().findById(request.getPublisherTypeId()).get());
		bundle.setState(providerUtil.getCartState());
		bundle.setUserId(token.getUserId());
		unitOfWork.getBundleRepo().save(bundle);

		for (BundleDbModel existingBundle : bundles) {
			for (ReservationDbModel existingReservation : existingBundle.getReservations()) {
				ReservationDbModel reservation = new ReservationDbModel();
				reservation.setEndDate(existingReservation.getEndDate());
				reservation.setCarId(existingReservation.getCarId());
				reservation.setStartDate(existingReservation.getStartDate());
				reservation.setTotalPrice(existingReservation.getTotalPrice());
				reservation.setWarrantyIncluded(existingReservation.isWarrantyIncluded());
				reservation.setBundle(bundle);

				unitOfWork.getReservationRepo().save(reservation);
			}
			unitOfWork.getBundleRepo().delete(existingBundle);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapResponse unbundle(SoapCartUnbundleRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateIdAndPublisherIdAndPublisherTypeId(
				token.getUserId(), providerUtil.getCartState().getId(), request.getPublisherId(), request.getPublisherTypeId());
		if (bundles.size() != 1 || bundles.get(0).getReservations().size() <= 1) {
			return response;
		}

		BundleDbModel existingBundle = bundles.get(0);

		for (ReservationDbModel existingReservation : existingBundle.getReservations()) {
			BundleDbModel bundle = new BundleDbModel();
			bundle.setPublisherId(existingBundle.getPublisherId());
			bundle.setPublisherType(existingBundle.getPublisherType());
			bundle.setState(existingBundle.getState());
			bundle.setUserId(existingBundle.getUserId());

			unitOfWork.getBundleRepo().save(bundle);

			ReservationDbModel reservation = new ReservationDbModel();
			reservation.setEndDate(existingReservation.getEndDate());
			reservation.setCarId(existingReservation.getCarId());
			reservation.setStartDate(existingReservation.getStartDate());
			reservation.setTotalPrice(existingReservation.getTotalPrice());
			reservation.setWarrantyIncluded(existingReservation.isWarrantyIncluded());
			reservation.setBundle(bundle);

			unitOfWork.getReservationRepo().save(reservation);
		}

		unitOfWork.getBundleRepo().delete(existingBundle);

		response.setSuccess(true);
		return response;
	}
}
