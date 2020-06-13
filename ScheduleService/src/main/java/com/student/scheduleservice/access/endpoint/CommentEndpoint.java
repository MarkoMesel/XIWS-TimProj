package com.student.scheduleservice.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.scheduleservice.internal.provider.CommentProvider;
import com.student.soap.contract.scheduleservice.SoapAddRatingRequest;
import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingRatingRequest;
import com.student.soap.contract.scheduleservice.SoapPendingRatingResponse;
import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Endpoint
public class CommentEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/scheduleservice/soap/contract";

	private CommentProvider commentProvider;

	@Autowired
	public CommentEndpoint(CommentProvider commentProvider) {
		super();
		this.commentProvider = commentProvider;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRatingsAndCommentsRequest")
	@ResponsePayload
	public SoapCarRatingsAndCommentsResponse getCommentsAndRatings(@RequestPayload SoapCarRatingsAndCommentsRequest request) {
		return commentProvider.getCarRatingsAndComments(request.getId());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPendingCommentsRequest")
	@ResponsePayload
	public SoapPendingCommentsResponse getPendingComments(@RequestPayload SoapPendingCommentsRequest request) {
		return commentProvider.getPendingComments(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapApproveCommentRequest")
	@ResponsePayload
	public SoapResponse approveComment(@RequestPayload SoapApproveCommentRequest request) {
		return commentProvider.approve(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapRejectCommentRequest")
	@ResponsePayload
	public SoapResponse rejectComment(@RequestPayload SoapRejectCommentRequest request) {
		return commentProvider.reject(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPendingRatingRequest")
	@ResponsePayload
	public SoapPendingRatingResponse rejectComment(@RequestPayload SoapPendingRatingRequest request) {
		return commentProvider.getPendingRating(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddRatingRequest")
	@ResponsePayload
	public SoapResponse addRating(@RequestPayload SoapAddRatingRequest request) {
		return commentProvider.addRating(request);
	}
}
