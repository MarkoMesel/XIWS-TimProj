package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.http.contract.HttpAddPriceRequest;
//import com.student.scheduleservice.data.dal.PriceDbModel;
//import com.student.scheduleservice.data.dal.PriceListDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.scheduleservice.jwt.Permission;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.scheduleservice.SoapAddPriceRequest;
//import com.student.soap.contract.scheduleservice.SoapDeletePriceRequest;
//import com.student.soap.contract.scheduleservice.SoapResponse;
import com.xiws.agentm.request.AddPriceRequestModel;
import com.xiws.agentm.scheduleservice.data.dal.PriceListDbModel;
import com.xiws.agentm.scheduleservice.data.dal.PriceDbModel;
import com.xiws.agentm.scheduleservice.data.repo.PriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.PriceRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import com.xiws.agentm.AuthenticationTokenParseResult;
import com.xiws.agentm.Permission;

@Controller
public class PriceController {
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	PriceListRepo priceListRepo;
	
	@Autowired
	PriceRepo priceRepo;
	
	/*
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public PriceController(ScheduleServiceClient scheduleServiceClient) {
		this.scheduleServiceClient = scheduleServiceClient;
	}
	*/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/price")
	public ResponseEntity<?> addPrice(@RequestBody AddPriceRequestModel request, @RequestHeader("token") String token) {
		/*
		SoapAddPriceRequest internalRequest = new SoapAddPriceRequest();

		internalRequest.setStartDate(request.getStartDate());
		internalRequest.setEndDate(request.getEndDate());
		internalRequest.setPrice(request.getPrice());
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
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (permission.getResourceId() != priceList.get().getPublisherId()
				|| permission.getResourceTypeId() != priceList.get().getPublisherType().getId()) 
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		//response.setAuthorized(true);

		Date requestedStartDate = request.getStartDate().toGregorianCalendar().getTime();
		Date requestedEndDate = request.getEndDate().toGregorianCalendar().getTime();
		
		// Is there already a price?
		List<PriceDbModel> existingPrice  = priceList.get()
				.getPrices()
				.stream()
				.filter(x-> datesOverlap(x.getStartDate(),x.getEndDate(), requestedStartDate, requestedEndDate))
				.collect(Collectors.toList());
		
		if (existingPrice.size()>0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		PriceDbModel price = new PriceDbModel();
		price.setPrice(request.getPrice());
		price.setPriceList(priceList.get());
		price.setStartDate(requestedStartDate);
		price.setEndDate(requestedEndDate);
		
		priceRepo.save(price);
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
	@DeleteMapping(path = "schedule/price/{id}")
	public ResponseEntity<?> deletePrice(@RequestHeader("token") String token, @PathVariable int id) {
		/*
		SoapDeletePriceRequest internalRequest = new SoapDeletePriceRequest();

		internalRequest.setToken(token);
		internalRequest.setId(id);

		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		Optional<PriceDbModel> price = priceRepo.findById(id);

		// Does the price exist?
		if (!price.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		Permission permission = tokenObj.getPermissions().stream().filter(p -> p.getPermissionId() == 4).findFirst()
				.orElse(null);
		
		if (!authanticated(tokenObj, permission)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (permission.getResourceId() != price.get().getPriceList().getPublisherId()
				|| permission.getResourceTypeId() != price.get().getPriceList().getPublisherType().getId()) 
		{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		//response.setAuthorized(true);
		
		try {
			priceRepo.delete(price.get());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
	
	public boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
		return !start1.after(end2) && !start2.after(end1);
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
