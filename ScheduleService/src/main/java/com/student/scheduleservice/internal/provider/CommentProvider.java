package com.student.scheduleservice.internal.provider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CommentDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.soap.contract.scheduleservice.Comment;
import com.student.soap.contract.scheduleservice.Rating;
import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
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
				.filter(reservation -> reservation.getBundle().getState().getId() == 6).collect(Collectors.toList());

		for (ReservationDbModel reservationIn : reservations) {
			List<CommentDbModel> comments = unitOfWork.getCommentRepo().findByReservationId(reservationIn.getId());

			if (comments.size() == 0) {
				continue;
			}

			Rating ratingAndComment = new Rating();
			ratingAndComment.setUserId(comments.get(0).getId());
			ratingAndComment.setUserName(providerUtil.fetchPublisherName("USER", comments.get(0).getPublisherId()));

			if (comments.get(0).getApproved()) {
				ratingAndComment.setComment(comments.get(0).getComment());
			}
			
			ratingAndComment.setDate(providerUtil.getXmlGregorianCalendar(comments.get(0).getUnixTimestamp().longValue()));
			
			ratingAndComment.setRating(reservationIn.getRating());

			for (int i = 1; i < comments.size(); i++) {
				CommentDbModel commentIn = comments.get(i);
				if (!commentIn.getApproved()) {
					continue;
				}
				Comment reply = new Comment();

				String publisherName = providerUtil.fetchPublisherName(commentIn.getPublisherType().getName(), commentIn.getPublisherId());
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
			commentOut.setPublisherName(providerUtil.fetchPublisherName(commentIn.getPublisherType().getName(), commentIn.getPublisherId()));
			commentOut.setComment(commentIn.getComment());
			commentOut.setDate(providerUtil.getXmlGregorianCalendar(commentIn.getUnixTimestamp().longValue()));

			response.getPendingComment().add(commentOut);
		}

		response.setSuccess(true);
		return response;
	}

}
