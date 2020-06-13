package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.CommentDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.soap.client.CarServiceClient;
import com.student.soap.contract.carservice.SoapCarRequest;
import com.student.soap.contract.carservice.SoapCarResponse;
import com.student.soap.contract.scheduleservice.Car;
import com.student.soap.contract.scheduleservice.Comment;
import com.student.soap.contract.scheduleservice.Rating;
import com.student.soap.contract.scheduleservice.SoapAddRatingRequest;
import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingRatingRequest;
import com.student.soap.contract.scheduleservice.SoapPendingRatingResponse;
import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("CommentProvider")
public class CommentProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;
	private ProviderUtil providerUtil;
	private CarServiceClient carServiceClient;

	@Autowired
	public CommentProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil,
			CarServiceClient carServiceClient) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
		this.carServiceClient = carServiceClient;
	}

	public SoapCarRatingsAndCommentsResponse getCarRatingsAndComments(int id) {
		SoapCarRatingsAndCommentsResponse response = new SoapCarRatingsAndCommentsResponse();

		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(id).stream()
				.filter(reservation -> reservation.getBundle().getState().getId() == 6).collect(Collectors.toList());

		for (ReservationDbModel reservationIn : reservations) {
			List<CommentDbModel> comments = unitOfWork.getCommentRepo().findByReservationId(reservationIn.getId());

			//Are there comments
			if (comments.size() == 0) {
				continue;
			}
			
			//Is the reservation comment aproved?
			if (comments.get(0).getApproved() == null || !comments.get(0).getApproved()) {
				continue;
			}

			Rating ratingAndComment = new Rating();
			ratingAndComment.setUserId(comments.get(0).getId());
			ratingAndComment.setUserName(providerUtil.fetchPublisherName("USER", comments.get(0).getPublisherId()));
			
			ratingAndComment.setComment(comments.get(0).getComment());

			ratingAndComment.setDate(providerUtil.getXmlGregorianCalendar(comments.get(0).getUnixTimestamp()));

			ratingAndComment.setRating(reservationIn.getRating());

			for (int i = 1; i < comments.size(); i++) {
				CommentDbModel commentIn = comments.get(i);
				if (!commentIn.getApproved()) {
					continue;
				}
				Comment reply = new Comment();

				String publisherName = providerUtil.fetchPublisherName(commentIn.getPublisherType().getName(),
						commentIn.getPublisherId());
				reply.setPublisherName(publisherName);

				reply.setComment(commentIn.getComment());
				reply.setPublisherId(commentIn.getPublisherId());
				reply.setPublisherTypeId(commentIn.getPublisherType().getId());
				reply.setPublisherTypeName(commentIn.getPublisherType().getName());
				reply.setDate(providerUtil.getXmlGregorianCalendar(commentIn.getUnixTimestamp().longValue()));

				ratingAndComment.getReply().add(reply);
			}

			response.getComment().add(ratingAndComment);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapResponse approve(SoapApproveCommentRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		Optional<CommentDbModel> comment = unitOfWork.getCommentRepo().findById(request.getCommentId());
		if (!comment.isPresent()) {
			return response;
		}

		comment.get().setApproved(true);

		unitOfWork.getCommentRepo().save(comment.get());

		response.setSuccess(true);
		return response;
	}

	public SoapResponse reject(SoapRejectCommentRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		Optional<CommentDbModel> comment = unitOfWork.getCommentRepo().findById(request.getCommentId());
		if (!comment.isPresent()) {
			return response;
		}

		unitOfWork.getCommentRepo().delete(comment.get());

		response.setSuccess(true);
		return response;
	}

	public SoapPendingCommentsResponse getPendingComments(SoapPendingCommentsRequest request) {
		SoapPendingCommentsResponse response = new SoapPendingCommentsResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || !token.getRoleName().equals("ADMIN")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<CommentDbModel> comments = unitOfWork.getCommentRepo().findByApproved(null);

		for (CommentDbModel commentIn : comments) {
			Comment commentOut = new Comment();
			commentOut.setId(commentIn.getId());
			commentOut.setPublisherId(commentIn.getPublisherId());
			commentOut.setPublisherTypeId(commentIn.getPublisherType().getId());
			commentOut.setPublisherTypeName(commentIn.getPublisherType().getName());
			commentOut.setPublisherName(providerUtil.fetchPublisherName(commentIn.getPublisherType().getName(),
					commentIn.getPublisherId()));
			commentOut.setComment(commentIn.getComment());
			commentOut.setDate(providerUtil.getXmlGregorianCalendar(commentIn.getUnixTimestamp()));

			response.getPendingComment().add(commentOut);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapPendingRatingResponse getPendingRating(SoapPendingRatingRequest request) {
		SoapPendingRatingResponse response = new SoapPendingRatingResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), 6);

		for (BundleDbModel bundle : bundles) {
			for (ReservationDbModel reservationIn : bundle.getReservations()) {
				if (reservationIn.getRating() != null) {
					continue;
				}

				Car reservationOut = new Car();

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut
						.setExtraCharges(reservationIn.getExtraCharges() == null ? 0 : reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = unitOfWork.getCarPriceListRepo()
						.findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp()))
						.findFirst().orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}

				// fetch car
				SoapCarRequest soapCarRequest = new SoapCarRequest();
				soapCarRequest.setId(reservationIn.getCarId());
				SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);

				if (soapCarResponse.isSuccess()) {
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
					String publisherName = providerUtil.fetchPublisherName(soapCarResponse.getCar().getPublisherTypeName(), soapCarResponse.getCar().getPublisherId());
					reservationOut.setPublisherName(publisherName);
				}

				response.getCar().add(reservationOut);
			}
		}

		response.setSuccess(true);
		return response;
	}

	public SoapResponse addRating(SoapAddRatingRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		Optional<ReservationDbModel> reservation = unitOfWork.getReservationRepo().findById(request.getReservationId());

		if (token.getUserId() != reservation.get().getBundle().getUserId()) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		//Is it already rated
		if (reservation.get().getRating()!=null) {
			return response;
		}
		
		//Is the new rating valid
		if (request.getRating() > 5 || request.getRating()<1) {
			return response;
		}

		reservation.get().setRating(request.getRating());
		unitOfWork.getReservationRepo().save(reservation.get());

		if(request.getComment()!=null && request.getComment().trim().length()>0) {
			
			CommentDbModel comment = new CommentDbModel();
			comment.setReservation(reservation.get());
			comment.setComment(request.getComment());
			comment.setPublisherId(token.getUserId());
			comment.setPublisherType(reservation.get().getBundle().getPublisherType());
			comment.setUnixTimestamp(Calendar.getInstance().getTimeInMillis());

			unitOfWork.getCommentRepo().save(comment);
		}

		response.setSuccess(true);
		return response;
	}
}
