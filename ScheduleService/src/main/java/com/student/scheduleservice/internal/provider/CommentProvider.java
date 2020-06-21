package com.student.scheduleservice.internal.provider;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.CommentDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.soap.contract.scheduleservice.Correspondence;
import com.student.soap.contract.scheduleservice.Rating;
import com.student.soap.contract.scheduleservice.SoapAddRatingRequest;
import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingRatingRequest;
import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("CommentProvider")
public class CommentProvider {

	private UnitOfWork unitOfWork;
	private JwtUtil jwtUtil;
	private ProviderUtil providerUtil;

	@Autowired
	public CommentProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	public SoapCarRatingsAndCommentsResponse getCarRatingsAndComments(int id) {
		SoapCarRatingsAndCommentsResponse response = new SoapCarRatingsAndCommentsResponse();

		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(id).stream()
				.filter(reservation -> reservation.getBundle().getState().getId() == providerUtil.getCompletedState().getId()).collect(Collectors.toList());

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
				Correspondence reply = new Correspondence();

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
			Correspondence commentOut = new Correspondence();
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

	public SoapReservationsResponse getPendingRating(SoapPendingRatingRequest request) {
		SoapReservationsResponse response = new SoapReservationsResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!token.isValid() || token.getUserId() == null || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<BundleDbModel> bundles = unitOfWork.getBundleRepo().findByUserIdAndStateId(token.getUserId(), providerUtil.getCompletedState().getId());

		for (BundleDbModel bundle : bundles) {
			for (ReservationDbModel reservationIn : bundle.getReservations()) {
				if (reservationIn.getRating() != null) {
					continue;
				}

				response.getReservation().add(providerUtil.getSoapReservations(reservationIn));
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
