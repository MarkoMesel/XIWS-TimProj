package com.student.scheduleservice.internal.provider;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.PriceListDbModel;
import com.student.scheduleservice.data.dal.PublisherTypeDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
import com.student.scheduleservice.jwt.JwtUtil;
import com.student.scheduleservice.jwt.Permission;
import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapCarPriceListRequest;
import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
import com.student.soap.contract.scheduleservice.SoapPrice;
import com.student.soap.contract.scheduleservice.SoapPriceList;
import com.student.soap.contract.scheduleservice.SoapPriceListResponse;
import com.student.soap.contract.scheduleservice.SoapPriceListsResponse;
import com.student.soap.contract.scheduleservice.SoapPublisherPriceListsRequest;
import com.student.soap.contract.scheduleservice.SoapResponse;
import com.student.soap.contract.scheduleservice.SoapSetCarPriceListRequest;

@Component("PriceListProvider")
public class PriceListProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;
	private JwtUtil jwtUtil;

	@Autowired
	public PriceListProvider(UnitOfWork unitOfWork, JwtUtil jwtUtil, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.jwtUtil = jwtUtil;
		this.providerUtil = providerUtil;
	}

	private boolean authanticated(AuthenticationTokenParseResult token, Permission permission) {

		if (!token.isValid() || (!token.getRoleName().equals("BASIC") && !token.getRoleName().equals("AGENT"))) {
			return false;
		}

		if (permission == null || permission.getResourceId() == null || permission.getResourceTypeId() == null) {
			return false;
		}

		return true;
	}

	public SoapResponse setCarPriceList(SoapSetCarPriceListRequest request) {
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

		CarPriceListDbModel carPriceList = new CarPriceListDbModel();

		carPriceList.setCarId(request.getCarId());
		carPriceList.setPriceList(priceList.get());
		carPriceList.setUnixTimestamp(new GregorianCalendar().getTimeInMillis());

		unitOfWork.getCarPriceListRepo().save(carPriceList);

		response.setSuccess(true);
		return response;
	}
	
	public SoapResponse addPriceList(SoapAddPriceListRequest request) {
		SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(token, permission)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		PriceListDbModel priceList = new PriceListDbModel();

		if (request.getName() == null || request.getName().trim().length() == 0) {
			response.setSuccess(false);
			return response;
		}

		priceList.setDiscountPercentage(request.getDiscountPercentage());
		priceList.setMileagePenalty(request.getMileagePenalty());
		priceList.setMileageThreshold(request.getMileageThreshold());
		priceList.setName(request.getName());
		priceList.setPublisherId(permission.getResourceId());

		PublisherTypeDbModel publisherType = unitOfWork.getPublisherTypeRepo().findById(permission.getResourceTypeId())
				.get();
		priceList.setPublisherType(publisherType);

		unitOfWork.getPriceListRepo().save(priceList);
		response.setSuccess(true);
		return response;
	}

	public SoapResponse deletePriceList(SoapDeletePriceListRequest request) {
		SoapResponse response = new SoapResponse();

		Optional<PriceListDbModel> priceList = unitOfWork.getPriceListRepo().findById(request.getId());

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
				|| permission.getResourceTypeId() != priceList.get().getPublisherType().getId()) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		try {
			unitOfWork.getPriceListRepo().delete(priceList.get());
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}

	public SoapPriceListsResponse getPublisherPriceLists(SoapPublisherPriceListsRequest request) {
		SoapPriceListsResponse response = new SoapPriceListsResponse();

		AuthenticationTokenParseResult token = jwtUtil.parseAuthenticationToken(request.getToken());

		Permission permission = token.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(token, permission)) {
			response.setAuthorized(false);
			return response;
		}

		response.setAuthorized(true);

		List<PriceListDbModel> priceLists = unitOfWork.getPriceListRepo()
				.findByPublisherIdAndPublisherTypeId(permission.getResourceId(), permission.getResourceTypeId());

		for (PriceListDbModel priceListIn : priceLists) {
			SoapPriceList priceListOut = new SoapPriceList();
			priceListOut.setDiscountPercentage(priceListIn.getDiscountPercentage());
			priceListOut.setId(priceListIn.getId());
			priceListOut.setMileagePenalty(priceListIn.getMileagePenalty());
			priceListOut.setMileageThreshold(priceListIn.getMileageThreshold());
			priceListOut.setName(priceListIn.getName());
			priceListOut.setWarrantyPrice(priceListIn.getWarrantyPrice());
			
			for(PriceDbModel priceIn : priceListIn.getPrices()) {
				SoapPrice priceOut = new SoapPrice();
				priceOut.setId(priceIn.getId());
				priceOut.setPrice(priceIn.getPrice());
				priceOut.setStartDate(providerUtil.getXmlGregorianCalendar(priceIn.getStartDate()));
				priceOut.setEndDate(providerUtil.getXmlGregorianCalendar(priceIn.getEndDate()));
				
				priceListOut.getPrice().add(priceOut);
			}
			
			response.getPriceList().add(priceListOut);
		}

		response.setSuccess(true);
		return response;
	}

	public SoapPriceListResponse getCarPricelist(SoapCarPriceListRequest request) {
		SoapPriceListResponse response = new SoapPriceListResponse();

		CarPriceListDbModel carPriceList = unitOfWork.getCarPriceListRepo().findByCarId(request.getCarId()).stream()
				.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		if (carPriceList == null || carPriceList.getPriceList() == null) {
			return response;
		}

		SoapPriceList priceListOut = new SoapPriceList();
		priceListOut.setDiscountPercentage(carPriceList.getPriceList().getDiscountPercentage());
		priceListOut.setId(carPriceList.getPriceList().getId());
		priceListOut.setMileagePenalty(carPriceList.getPriceList().getMileagePenalty());
		priceListOut.setMileageThreshold(carPriceList.getPriceList().getMileageThreshold());
		priceListOut.setName(carPriceList.getPriceList().getName());
		priceListOut.setWarrantyPrice(carPriceList.getPriceList().getWarrantyPrice());

		for(PriceDbModel priceIn : carPriceList.getPriceList().getPrices()) {
			SoapPrice priceOut = new SoapPrice();
			priceOut.setId(priceIn.getId());
			priceOut.setPrice(priceIn.getPrice());
			priceOut.setStartDate(providerUtil.getXmlGregorianCalendar(priceIn.getStartDate()));
			priceOut.setEndDate(providerUtil.getXmlGregorianCalendar(priceIn.getEndDate()));
			
			priceListOut.getPrice().add(priceOut);
		}
		
		response.setPriceList(priceListOut);

		response.setSuccess(true);
		return response;
	}
}
