package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.scheduleservice.data.dal.CarPriceListDbModel;
//import com.student.scheduleservice.data.dal.PublisherTypeDbModel;
//import com.student.soap.contract.scheduleservice.SoapPrice;
//import com.student.soap.contract.scheduleservice.SoapPriceList;
//import com.student.soap.contract.scheduleservice.SoapPriceListResponse;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.http.contract.HttpAddCarPriceListRequest;
//import com.student.http.contract.HttpAddPriceListRequest;
//import com.student.http.contract.HttpPriceListResponse;
//import com.student.internal.translator.Translator;
//import com.student.scheduleservice.data.dal.PriceDbModel;
//import com.student.scheduleservice.data.dal.PriceListDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.scheduleservice.jwt.Permission;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.SoapAddPriceListRequest;
//import com.student.soap.contract.scheduleservice.SoapCarPriceListRequest;
//import com.student.soap.contract.scheduleservice.SoapDeletePriceListRequest;
//import com.student.soap.contract.scheduleservice.SoapPrice;
//import com.student.soap.contract.scheduleservice.SoapPriceList;
//import com.student.soap.contract.scheduleservice.SoapPriceListResponse;
//import com.student.soap.contract.scheduleservice.SoapPriceListsResponse;
//import com.student.soap.contract.scheduleservice.SoapPublisherPriceListsRequest;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.soap.contract.scheduleservice.SoapSetCarPriceListRequest;
import com.xiws.agentm.response.PriceListResponseModel;
import com.xiws.agentm.response.PriceResponseModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;
import com.xiws.agentm.request.AddCarPriceListRequestModel;
import com.xiws.agentm.request.AddPriceListRequestModel;
import com.xiws.agentm.scheduleservice.data.dal.PriceListDbModel;
import com.xiws.agentm.scheduleservice.data.dal.PriceDbModel;
import com.xiws.agentm.scheduleservice.data.dal.SchedulePublisherTypeDbModel;
import com.xiws.agentm.scheduleservice.data.dal.CarPriceListDbModel;
import com.xiws.agentm.scheduleservice.data.repo.CarPriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.PriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.SchedulePublisherTypeRepo;

@Controller
public class PriceListController {	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	PriceListRepo priceListRepo;
	
	@Autowired
	CarPriceListRepo carPriceListRepo;
	
	@Autowired
	SchedulePublisherTypeRepo schedulePublisherTypeRepo;
	
	/*
	private ScheduleServiceClient scheduleServiceClient;
	private Translator translator;

	@Autowired
	public PriceListController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	*/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/priceLists")
	public ResponseEntity<List<PriceListResponseModel>> getPublisherPriceLists(@RequestHeader("token") String token) {
		/*
		SoapPublisherPriceListsRequest request = new SoapPublisherPriceListsRequest();
		request.setToken(token);
		SoapPriceListsResponse internalResponse = scheduleServiceClient.send(request);
		*/
		//SoapPriceListsResponse response = new SoapPriceListsResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		//response.setAuthorized(true);
		
		List<PriceListResponseModel> internalResponse = new ArrayList<PriceListResponseModel>();
		
		List<PriceListDbModel> priceLists = priceListRepo
				.findByPublisherIdAndPublisherTypeId(permission.getResourceId(), permission.getResourceTypeId());

		for (PriceListDbModel priceListIn : priceLists) {
			PriceListResponseModel priceListOut = new PriceListResponseModel();
			priceListOut.setDiscountPercentage(priceListIn.getDiscountPercentage());
			priceListOut.setId(priceListIn.getId());
			priceListOut.setMileagePenalty(priceListIn.getMileagePenalty());
			priceListOut.setMileageThreshold(priceListIn.getMileageThreshold());
			priceListOut.setName(priceListIn.getName());
			priceListOut.setWarrantyPrice(priceListIn.getWarrantyPrice());
			
			for(PriceDbModel priceIn : priceListIn.getPrices()) {
				PriceResponseModel priceOut = new PriceResponseModel();
				priceOut.setId(priceIn.getId());
				priceOut.setPrice(priceIn.getPrice());
				
				final GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(priceIn.getStartDate());
				try {
					priceOut.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				
				//priceOut.setStartDate(providerUtil.getXmlGregorianCalendar(priceIn.getStartDate()));
				
				final GregorianCalendar calendar2 = new GregorianCalendar();
				calendar2.setTime(priceIn.getStartDate());
				try {
					priceOut.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				
				//priceOut.setEndDate(providerUtil.getXmlGregorianCalendar(priceIn.getEndDate()));
				
				priceListOut.getPrices().add(priceOut);
			}
			
			internalResponse.add(priceListOut);
		}

		return new ResponseEntity<List<PriceListResponseModel>>(internalResponse, HttpStatus.OK);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<HttpPriceListResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/schedule/priceList")
	public ResponseEntity<?> addPriceList(@RequestBody AddPriceListRequestModel request,
			@RequestHeader("token") String token) {
		/*
		SoapAddPriceListRequest internalRequest = new SoapAddPriceListRequest();
		internalRequest.setDiscountPercentage(request.getDiscountPercentage());
		internalRequest.setMileagePenalty(request.getMileagePenalty());
		internalRequest.setMileageThreshold(request.getMileageThreshold());
		internalRequest.setName(request.getName());
		internalRequest.setWarrantyPrice(request.getWarrantyPrice());
		internalRequest.setToken(token);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		//response.setAuthorized(true);

		PriceListDbModel priceList = new PriceListDbModel();

		if (request.getName() == null || request.getName().trim().length() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		priceList.setDiscountPercentage(request.getDiscountPercentage());
		priceList.setMileagePenalty(request.getMileagePenalty());
		priceList.setMileageThreshold(request.getMileageThreshold());
		priceList.setName(request.getName());
		priceList.setPublisherId(permission.getResourceId());

		SchedulePublisherTypeDbModel publisherType = schedulePublisherTypeRepo.findById(permission.getResourceTypeId())
				.get();
		priceList.setPublisherType(publisherType);

		priceListRepo.save(priceList);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping(path = "schedule/priceList/{id}")
	public ResponseEntity<?> deletePriceList(@RequestHeader("token") String token, @PathVariable int id) {
		/*
		SoapDeletePriceListRequest internalRequest = new SoapDeletePriceListRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		Optional<PriceListDbModel> priceList = priceListRepo.findById(id);

		// Does the price list exist?
		if (!priceList.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (permission.getResourceId() != priceList.get().getPublisherId()
				|| permission.getResourceTypeId() != priceList.get().getPublisherType().getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		//response.setAuthorized(true);

		try {
			priceListRepo.delete(priceList.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/schedule/car/priceList")
	public ResponseEntity<?> setCarPriceList(@RequestBody AddCarPriceListRequestModel request,
			@RequestHeader("token") String token) {
		/*
		SoapSetCarPriceListRequest internalRequest = new SoapSetCarPriceListRequest();

		internalRequest.setCarId(request.getCarId());
		internalRequest.setPriceListId(request.getPriceListId());
		internalRequest.setToken(token);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		Optional<PriceListDbModel> priceList = priceListRepo.findById(request.getPriceListId());

		// Does the price list exist?
		if (!priceList.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);

		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (permission.getResourceId() != priceList.get().getPublisherId()
				|| permission.getResourceTypeId() != priceList.get().getPublisherType().getId()) 
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		//response.setAuthorized(true);

		CarPriceListDbModel carPriceList = new CarPriceListDbModel();

		carPriceList.setCarId(request.getCarId());
		carPriceList.setPriceList(priceList.get());
		carPriceList.setUnixTimestamp(new GregorianCalendar().getTimeInMillis());

		carPriceListRepo.save(carPriceList);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		if (internalResponse.isAuthorized() != null && !internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{carId}/priceList")
	public ResponseEntity<PriceListResponseModel> getCarPriceList(@PathVariable int carId) {
		/*
		SoapCarPriceListRequest request = new SoapCarPriceListRequest();
		request.setCarId(carId);
		SoapPriceListResponse internalResponse = scheduleServiceClient.send(request);
		*/
		//SoapPriceListResponse response = new SoapPriceListResponse();

		CarPriceListDbModel carPriceList = carPriceListRepo.findByCarId(carId).stream()
				.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		if (carPriceList == null || carPriceList.getPriceList() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		PriceListResponseModel priceListOut = new PriceListResponseModel();
		priceListOut.setDiscountPercentage(carPriceList.getPriceList().getDiscountPercentage());
		priceListOut.setId(carPriceList.getPriceList().getId());
		priceListOut.setMileagePenalty(carPriceList.getPriceList().getMileagePenalty());
		priceListOut.setMileageThreshold(carPriceList.getPriceList().getMileageThreshold());
		priceListOut.setName(carPriceList.getPriceList().getName());
		priceListOut.setWarrantyPrice(carPriceList.getPriceList().getWarrantyPrice());

		for(PriceDbModel priceIn : carPriceList.getPriceList().getPrices()) {
			PriceResponseModel priceOut = new PriceResponseModel();
			priceOut.setId(priceIn.getId());
			priceOut.setPrice(priceIn.getPrice());
			
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(priceIn.getStartDate());
			try {
				priceOut.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			//priceOut.setStartDate(providerUtil.getXmlGregorianCalendar(priceIn.getStartDate()));
			
			final GregorianCalendar calendar2 = new GregorianCalendar();
			calendar2.setTime(priceIn.getStartDate());
			try {
				priceOut.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			
			//priceOut.setStartDate(providerUtil.getXmlGregorianCalendar(priceIn.getStartDate()));
			//priceOut.setEndDate(providerUtil.getXmlGregorianCalendar(priceIn.getEndDate()));
			
			priceListOut.getPrices().add(priceOut);
		}
		
		//response.setPriceList(priceListOut);

		return new ResponseEntity<PriceListResponseModel>(priceListOut, HttpStatus.OK);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PriceListResponseModel>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}
	
	public AuthenticationTokenParseResult parseAuthenticationToken(String token) {
		AuthenticationTokenParseResult result = new AuthenticationTokenParseResult();
		try {
			Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			Claims claims = parseClaimsJws.getBody();
			result.setUserId((Integer) claims.get("userId"));
			result.setIssuer((String) claims.get("iss"));
			result.setPurpose((String) claims.get("purpose"));
			Object permissionsObject = claims.get("permissions");
			ObjectMapper mapper = new ObjectMapper();
			List<Permission> permissions= mapper.convertValue(permissionsObject, new TypeReference<List<Permission>>() { });
			result.setPermissions(permissions);
			result.setRoleName((String) claims.get("roleName"));
			result.setRoleId((Integer) claims.get("roleId"));

			if (result.getIssuer() == null || !result.getIssuer().equals(issuer) || result.getUserId() == null
					|| result.getPurpose() == null || !result.getPurpose().equals(AUTHENTICATION_STRING)
					|| (!(result.getRoleName().equals("BASIC") && result.getRoleId() == 1)
							&& !(result.getRoleName().equals("AGENT") && result.getRoleId() == 2)
							&& !(result.getRoleName().equals("ADMIN") && result.getRoleId() == 3))) {
				result = new AuthenticationTokenParseResult();
				result.setValid(false);
				return result;
			}
			result.setValid(true);
			return result;
		} catch (Exception e) {
			result = new AuthenticationTokenParseResult();
			result.setValid(false);
			return result;
		}
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
}
