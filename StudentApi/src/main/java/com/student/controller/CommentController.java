package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpCommentResponse;
import com.student.http.contract.HttpRepliesAndCommentsResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Controller
public class CommentController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public CommentController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/comments")
	public ResponseEntity<List<HttpRepliesAndCommentsResponse>> getComments(@PathVariable("id") int id) {
		SoapCarRatingsAndCommentsRequest request = new SoapCarRatingsAndCommentsRequest();
		request.setId(id);
		SoapCarRatingsAndCommentsResponse internalResponse = scheduleServiceClient
				.send(translator.translateRatingsAndComments(id));
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/comments/pending")
	public ResponseEntity<List<HttpCommentResponse>> getPendingComments(@RequestHeader("token") String token) {
		SoapPendingCommentsRequest internalRequest = new SoapPendingCommentsRequest();
		internalRequest.setToken(token);
		SoapPendingCommentsResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpCommentResponse>>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/comments/{id}/approve")
	public ResponseEntity<?> approve(@RequestHeader("token") String token, @PathVariable("id") int id) {
		SoapApproveCommentRequest internalRequest = new SoapApproveCommentRequest();
		internalRequest.setCommentId(id);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/comments/{id}/reject")
	public ResponseEntity<?> reject(@RequestHeader("token") String token, @PathVariable("id") int id) {
		SoapRejectCommentRequest internalRequest = new SoapRejectCommentRequest();
		internalRequest.setCommentId(id);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
