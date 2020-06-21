package com.student.scheduleservice.internal.provider;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.PriceListDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;

@Component("PriceProvider")
public class PriceProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public PriceProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	private boolean authanticated(AuthenticationTokenParseResult token, Permission permission) {

		if (!token.isValid() || (!token.getRoleName().equals("BASIC") && !token.getRoleName().equals("AGENT"))) {
			return false;
		}

		if (permission == null) {
			return false;
		}
		
		return true;
	}

	public SoapResponse addPrice(SoapAddPriceRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<PriceListDbModel> priceList = unitOfWork.getPriceListRepo().findById(request.getPriceListId());

		// Does the price list exist?
		if (!priceList.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(token, permission)) {
			response.setAuthorized(false);
			return response;
		}
		
		if (permission.getResourceId() != priceList.get().getPublisherId()
				|| permission.getResourceTypeId() != priceList.get().getPublisherType().getId()) 
		{
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);

		Date requestedStartDate = request.getStartDate().toGregorianCalendar().getTime();
		Date requestedEndDate = request.getEndDate().toGregorianCalendar().getTime();
		
		// Is there already a price?
		List<PriceDbModel> existingPrice  = priceList.get()
				.getPrices()
				.stream()
				.filter(x-> providerUtil.datesOverlap(x.getStartDate(),x.getEndDate(), requestedStartDate, requestedEndDate))
				.collect(Collectors.toList());
		
		if (existingPrice.size()>0) {
			response.setSuccess(false);
			return response;
		}
		
		PriceDbModel price = new PriceDbModel();
		price.setPrice(request.getPrice());
		price.setPriceList(priceList.get());
		price.setStartDate(requestedStartDate);
		price.setEndDate(requestedEndDate);
		
		unitOfWork.getPriceRepo().save(price);
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deletePrice(SoapDeletePriceRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<PriceDbModel> price = unitOfWork.getPriceRepo().findById(request.getId());

		// Does the price exist?
		if (!price.isPresent()) {
			response.setSuccess(false);
			return response;
		}

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(token, permission)) {
			response.setAuthorized(false);
			return response;
		}
		
		if (permission.getResourceId() != price.get().getPriceList().getPublisherId()
				|| permission.getResourceTypeId() != price.get().getPriceList().getPublisherType().getId()) 
		{
			response.setAuthorized(false);
			return response;
		}
		
		response.setAuthorized(true);
		
		try {
			unitOfWork.getPriceRepo().delete(price.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}
		
		response.setSuccess(true);
		return response;
	}
}
