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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.student.http.contract.HttpAddMessageRequest;
import com.student.http.contract.HttpCorrespondenceResponse;
import com.student.internal.translator.Translator;
import com.student.soap.client.ScheduleServiceClient;
import com.student.soap.contract.scheduleservice.SoapAddMessageRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Controller
public class ChatController {
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public ChatController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/bundle/{id}/chat")
	public ResponseEntity<List<HttpCorrespondenceResponse>> getMessages(@PathVariable("id") int id, @RequestHeader("token") String token) {
		
		SoapMessagesRequest internalRequest = new SoapMessagesRequest();
		internalRequest.setBundleId(id);
		internalRequest.setToken(token);
		
		SoapMessagesResponse internalResponse = scheduleServiceClient.send(internalRequest);
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/chat")
	public ResponseEntity<?> addRating(@RequestHeader("token") String token, @RequestBody HttpAddMessageRequest request) {
		SoapAddMessageRequest internalRequest = new SoapAddMessageRequest();
		internalRequest.setMessage(request.getMessage());
		internalRequest.setBundleId(request.getBundleId());
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
