package com.xiws.agentm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

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

//import com.student.data.dal.UserDbModel;
//import com.student.http.contract.HttpAddMessageRequest;
//import com.student.http.contract.HttpCorrespondenceResponse;
//import com.student.internal.translator.Translator;
import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;
import com.xiws.agentm.scheduleservice.data.dal.MessageDbModel;
import com.xiws.agentm.scheduleservice.data.dal.SchedulePublisherTypeDbModel;
//import com.student.scheduleservice.data.dal.PublisherTypeDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.Correspondence;
//import com.student.soap.contract.scheduleservice.SoapAddMessageRequest;
//import com.student.soap.contract.scheduleservice.SoapMessagesRequest;
//import com.student.soap.contract.scheduleservice.SoapMessagesResponse;
//import com.student.soap.contract.scheduleservice.SoapResponse;
import com.xiws.agentm.carservice.data.dal.CarPublisherTypeDbModel;
import com.xiws.agentm.carservice.data.repo.CarPublisherTypeRepo;
import com.xiws.agentm.request.AddMessageRequestModel;
import com.xiws.agentm.response.CorrespondenceResponseModel;

import  com.xiws.agentm.scheduleservice.data.repo.BundleRepo;
import com.xiws.agentm.scheduleservice.data.repo.MessageRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationStateRepo;
import com.xiws.agentm.scheduleservice.data.repo.SchedulePublisherTypeRepo;
import com.xiws.agentm.userservice.data.repo.UserRepo;
import com.xiws.agentm.userservice.data.dal.UserDbModel;

@Controller
public class ChatController {
	
	@Autowired
	BundleRepo bundleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ReservationStateRepo reservationStateRepo;
	
	@Autowired
	SchedulePublisherTypeRepo schedulePublisherTypeRepo;
	
	@Autowired
	MessageRepo messageRepo;
	
	/*
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;
	
	@Autowired
	public ChatController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	*/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/bundle/{id}/chat")
	public ResponseEntity<List<CorrespondenceResponseModel>> getMessages(@PathVariable("id") int id, @RequestHeader("token") String token) {
		
		Optional<BundleDbModel> bundle = bundleRepo.findById(id);
		
		List<CorrespondenceResponseModel> internalResponse = new ArrayList<CorrespondenceResponseModel>();
		for(MessageDbModel messageIn : bundle.get().getMessages()) {
			CorrespondenceResponseModel messageOut = new CorrespondenceResponseModel();
			messageOut.setComment(messageIn.getMessage());
			messageOut.setId(messageIn.getId());
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(messageIn.getUnixTimestamp());
			try {
				messageOut.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			messageOut.setPublisherId(messageIn.getPublisherId());
			UserDbModel user = userRepo.findById(messageIn.getPublisherId()).get();
			messageOut.setPublisherName(user.getFirstName() + " " + user.getLastName());
			messageOut.setPublisherTypeId(messageIn.getPublisherType().getId());
			messageOut.setPublisherTypeName(messageIn.getPublisherType().getName());
			
			internalResponse.add(messageOut);
		}
		return new ResponseEntity<>(internalResponse, HttpStatus.OK);
		
		/*
		SoapMessagesRequest internalRequest = new SoapMessagesRequest();
		internalRequest.setBundleId(id);
		internalRequest.setToken(token);
		
		SoapMessagesResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapMessagesResponse response = new SoapMessagesResponse();

		// Does the bundle exist? Is it in paid or completed state?
		/*
		if (!bundle.isPresent() || (bundle.get().getState().getId() != providerUtil.getPaidState().getId() && bundle.get().getState().getId() != providerUtil.getCompletedState().getId())) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!authanticated(token, bundle.get())) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		*/
		
			//response.getMessage().add(messageOut);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/chat")
	public ResponseEntity<?> addRating(@RequestHeader("token") String token, @RequestBody AddMessageRequestModel request) {
		/*
		SoapAddMessageRequest internalRequest = new SoapAddMessageRequest();
		internalRequest.setMessage(request.getMessage());
		internalRequest.setBundleId(request.getBundleId());
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		
		//SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = bundleRepo.findById(request.getBundleId());

		// Does the bundle exist? Is it in paid or completed state?
		if (!bundle.isPresent() || (bundle.get().getState().getId() != reservationStateRepo.findById(4).get().getId() && bundle.get().getState().getId() != reservationStateRepo.findById(4).get().getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		/*
		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!authanticated(token, bundle.get())) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		*/
		
		if(request.getMessage() == null || request.getMessage().trim().length() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		// Who sent the message?
		int senderId = bundle.get().getPublisherId();
		SchedulePublisherTypeDbModel senderType = schedulePublisherTypeRepo.findById(2).get();

		MessageDbModel message = new MessageDbModel();
		message.setBundle(bundle.get());
		message.setMessage(request.getMessage());
		message.setPublisherId(senderId);
		message.setPublisherType(senderType);
		Date currentDateTime = new Date();
		message.setUnixTimestamp(currentDateTime.getTime());

		messageRepo.save(message);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		*/
	}
}
