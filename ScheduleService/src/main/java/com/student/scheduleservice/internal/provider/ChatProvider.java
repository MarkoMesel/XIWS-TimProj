package com.student.scheduleservice.internal.provider;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.BundleDbModel;
import com.student.scheduleservice.data.dal.MessageDbModel;
import com.student.scheduleservice.data.dal.PublisherTypeDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.Correspondence;
import com.student.soap.contract.scheduleservice.SoapAddMessageRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesRequest;
import com.student.soap.contract.scheduleservice.SoapMessagesResponse;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("ChatProvider")
public class ChatProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public ChatProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	private boolean authanticated(AuthenticationTokenParseResult token, BundleDbModel bundle) {
		
		if(!token.isValid() || (!token.getRoleName().equals("BASIC") && !token.getRoleName().equals("AGENT"))) {
			return false;
		}
		
		Permission permission = token.getPermissions().stream()
				.filter(p -> p.getPermissionId() == 6 ).findFirst().orElse(null);
		
		boolean isClient = token.getRoleName().equals("BASIC") && token.getUserId()==bundle.getUserId();
		
		boolean isPublisher = permission != null && permission.getResourceId()==bundle.getPublisherId() && permission.getResourceTypeId()==bundle.getPublisherType().getId(); 
		
		return isClient || isPublisher;
	}

	public SoapResponse addMessage(SoapAddMessageRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<BundleDbModel> bundle = unitOfWork.getBundleRepo().findById(request.getBundleId());

		// Does the bundle exist? Is it in paid state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != providerUtil.getPaidState().getId()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!authanticated(token, bundle.get())) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);

		if(request.getMessage() == null || request.getMessage().trim().length() == 0) {
			return response;
		}
		
		// Who sent the message?
		int senderId = token.getRoleName().equals("BASIC") ? bundle.get().getUserId() : bundle.get().getPublisherId();
		PublisherTypeDbModel senderType = unitOfWork.getPublisherTypeRepo().findById(token.getRoleId()).get();

		MessageDbModel message = new MessageDbModel();
		message.setBundle(bundle.get());
		message.setMessage(request.getMessage());
		message.setPublisherId(senderId);
		message.setPublisherType(senderType);
		Date currentDateTime = new Date();
		message.setUnixTimestamp(currentDateTime.getTime());

		unitOfWork.getMessageRepo().save(message);
		response.setSuccess(true);
		return response;
	}

	public SoapMessagesResponse getMessages(SoapMessagesRequest request) {
		SoapMessagesResponse response = new SoapMessagesResponse();

		Optional<BundleDbModel> bundle = unitOfWork.getBundleRepo().findById(request.getBundleId());

		// Does the bundle exist? Is it in paid state?
		if (!bundle.isPresent() || bundle.get().getState().getId() != providerUtil.getPaidState().getId()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		if (!authanticated(token, bundle.get())) {
			response.setAuthorized(false);
			return response;
		}
		response.setAuthorized(true);
		
		for(MessageDbModel messageIn : bundle.get().getMessages()) {
			Correspondence messageOut = new Correspondence();
			messageOut.setComment(messageIn.getMessage());
			messageOut.setId(messageIn.getId());
			messageOut.setDate(providerUtil.getXmlGregorianCalendar(messageIn.getUnixTimestamp()));
			messageOut.setPublisherId(messageIn.getPublisherId());
			messageOut.setPublisherName(providerUtil.fetchPublisherName(messageIn.getPublisherType().getName(), messageIn.getPublisherId()));
			messageOut.setPublisherTypeId(messageIn.getPublisherType().getId());
			messageOut.setPublisherTypeName(messageIn.getPublisherType().getName());
			
			response.getMessage().add(messageOut);
		}
		response.setSuccess(true);
		return response;
	}
}
