package com.xiws.agentm.controller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.student.agentservice.data.dal.AgentDbModel;
//import com.student.data.dal.CarImageDbModel;
//import com.student.http.contract.HttpAddRatingRequest;
//import com.student.http.contract.HttpCorrespondenceResponse;
//import com.student.http.contract.HttpRepliesAndCommentsResponse;
//import com.student.http.contract.HttpReservationResponse;
//import com.student.internal.translator.Translator;
//import com.student.scheduleservice.data.dal.BundleDbModel;
//import com.student.scheduleservice.data.dal.CarPriceListDbModel;
//import com.student.scheduleservice.data.dal.CommentDbModel;
//import com.student.scheduleservice.data.dal.ReservationDbModel;
//import com.student.scheduleservice.jwt.AuthenticationTokenParseResult;
//import com.student.scheduleservice.jwt.Permission;
//import com.student.soap.client.ScheduleServiceClient;
//import com.student.soap.contract.agentservice.SoapAgentByIdResponse;
//import com.student.soap.contract.carservice.SoapCarRequest;
//import com.student.soap.contract.carservice.SoapCarResponse;
//import com.student.soap.contract.scheduleservice.Correspondence;
//import com.student.soap.contract.scheduleservice.Rating;
//import com.student.soap.contract.scheduleservice.Reservation;
//import com.student.soap.contract.scheduleservice.SoapAddRatingRequest;
//import com.student.soap.contract.scheduleservice.SoapApproveCommentRequest;
//import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsRequest;
//import com.student.soap.contract.scheduleservice.SoapCarRatingsAndCommentsResponse;
//import com.student.soap.contract.scheduleservice.SoapPendingCommentsRequest;
//import com.student.soap.contract.scheduleservice.SoapPendingCommentsResponse;
//import com.student.soap.contract.scheduleservice.SoapPendingRatingRequest;
//import com.student.soap.contract.scheduleservice.SoapReservationsResponse;
//import com.student.soap.contract.scheduleservice.SoapRejectCommentRequest;
//import com.student.soap.contract.scheduleservice.SoapResponse;
//import com.student.soap.contract.userservice.SoapGetResponse;
import com.xiws.agentm.response.CorrespondenceResponseModel;
import com.xiws.agentm.response.RepliesAndCommentsResponseModel;
import com.xiws.agentm.response.ReservationResponseModel;
import com.xiws.agentm.scheduleservice.data.dal.ReservationDbModel;
import com.xiws.agentm.scheduleservice.data.dal.CommentDbModel;
import com.xiws.agentm.scheduleservice.data.dal.BundleDbModel;
import com.xiws.agentm.scheduleservice.data.dal.CarPriceListDbModel;
import com.xiws.agentm.scheduleservice.data.repo.CommentRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationRepo;
import com.xiws.agentm.scheduleservice.data.repo.ReservationStateRepo;
import com.xiws.agentm.scheduleservice.data.repo.CarPriceListRepo;
import com.xiws.agentm.scheduleservice.data.repo.BundleRepo;
import com.xiws.agentm.userservice.data.dal.UserDbModel;
import com.xiws.agentm.userservice.data.repo.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import com.xiws.agentm.agentservice.data.dal.AgentDbModel;
import com.xiws.agentm.agentservice.data.repo.AgentRepo;
import com.xiws.agentm.carservice.data.dal.CarDbModel;
import com.xiws.agentm.carservice.data.dal.CarImageDbModel;
import com.xiws.agentm.carservice.data.repo.CarRepo;
import com.xiws.agentm.request.AddRatingRequestModel;

import com.xiws.agentm.*;


@Controller
public class CommentController {
	
	private static String issuer = "user@student.com";
	private static String AUTHENTICATION_STRING = "AUTHENTICATION";
	private PublicKey publicKey;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	ReservationStateRepo reservationStateRepo;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AgentRepo agentRepo;
	
	@Autowired
	BundleRepo bundleRepo;
	
	@Autowired
	CarPriceListRepo carPriceListRepo;
	
	@Autowired
	CarRepo carRepo;
	
	/*
	private Translator translator;
	private ScheduleServiceClient scheduleServiceClient;

	@Autowired
	public CommentController(ScheduleServiceClient scheduleServiceClient, Translator translator) {
		this.scheduleServiceClient = scheduleServiceClient;
		this.translator = translator;
	}
	*/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/car/{id}/comments")
	public ResponseEntity<List<RepliesAndCommentsResponseModel>> getComments(@PathVariable("id") int id) {
		
		List<RepliesAndCommentsResponseModel> internalResponse = new ArrayList<RepliesAndCommentsResponseModel>();
		
		List<ReservationDbModel> reservations = reservationRepo.findByCarId(id).stream()
				.filter(reservation -> reservation.getBundle().getState().getId() == reservationStateRepo.findById(6).get().getId()).collect(Collectors.toList());

		for (ReservationDbModel reservationIn : reservations) {
			List<CommentDbModel> comments = commentRepo.findByReservationId(reservationIn.getId());

			//Are there comments
			if (comments.size() == 0) {
				continue;
			}
			
			//Is the reservation comment aproved?
			if (comments.get(0).getApproved() == null || !comments.get(0).getApproved()) {
				continue;
			}

			RepliesAndCommentsResponseModel ratingAndComment = new RepliesAndCommentsResponseModel();
			ratingAndComment.setUserId(comments.get(0).getId());
			
			UserDbModel userResponse = userRepo.findById( comments.get(0).getPublisherId()).get();
			
			ratingAndComment.setUserName(userResponse.getFirstName() + " " + userResponse.getLastName());
			
			ratingAndComment.setComment(comments.get(0).getComment());
			
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(comments.get(0).getUnixTimestamp());
			try {
				ratingAndComment.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
			}
			//ratingAndComment.setDate(providerUtil.getXmlGregorianCalendar(comments.get(0).getUnixTimestamp()));

			ratingAndComment.setRating(reservationIn.getRating());

			for (int i = 1; i < comments.size(); i++) {
				CommentDbModel commentIn = comments.get(i);
				if (!commentIn.getApproved()) {
					continue;
				}
				CorrespondenceResponseModel reply = new CorrespondenceResponseModel();
				String publisherName = "";
				String publisherTypeName = commentIn.getPublisherType().getName();
				if (publisherTypeName.equals("USER")) {
					try {
						UserDbModel user = userRepo.findById(commentIn.getPublisherId()).get();
						publisherName = user.getFirstName() + " " + user.getLastName();
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				// If agent
				if (publisherTypeName.equals("AGENT")) {
					try {
						AgentDbModel agent = agentRepo.findById(commentIn.getPublisherId()).get();
						publisherName = agent.getName();
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				
				reply.setPublisherName(publisherName);

				reply.setComment(commentIn.getComment());
				reply.setPublisherId(commentIn.getPublisherId());
				reply.setPublisherTypeId(commentIn.getPublisherType().getId());
				reply.setPublisherTypeName(commentIn.getPublisherType().getName());
				
				final GregorianCalendar calendar2 = new GregorianCalendar();
				calendar2.setTimeInMillis(commentIn.getUnixTimestamp().longValue());
				try {
					reply.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				
				//reply.setDate(providerUtil.getXmlGregorianCalendar(commentIn.getUnixTimestamp().longValue()));

				ratingAndComment.getReplies().add(reply);
			}

			internalResponse.add(ratingAndComment);
		}

		return new ResponseEntity<>(internalResponse, HttpStatus.OK);
		
		/*
		SoapCarRatingsAndCommentsRequest internalRequest = new SoapCarRatingsAndCommentsRequest();
		internalRequest.setId(id);
		SoapCarRatingsAndCommentsResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapCarRatingsAndCommentsResponse response = new SoapCarRatingsAndCommentsResponse();
		
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/comments/pending")
	public ResponseEntity<List<CorrespondenceResponseModel>> getPendingComments(@RequestHeader("token") String token) {
		/*
		SoapPendingCommentsRequest internalRequest = new SoapPendingCommentsRequest();
		internalRequest.setToken(token);
		SoapPendingCommentsResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapPendingCommentsResponse response = new SoapPendingCommentsResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);
		/*
		if (!tokenObj.isValid() || !tokenObj.getRoleName().equals("ADMIN")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		*/

		//response.setAuthorized(true);
		
		List<CorrespondenceResponseModel> internalResponse = new ArrayList<CorrespondenceResponseModel>();
		
		List<CommentDbModel> comments = commentRepo.findByApproved(null);
		
		for (CommentDbModel commentIn : comments) {
			CorrespondenceResponseModel commentOut = new CorrespondenceResponseModel();
			commentOut.setId(commentIn.getId());
			commentOut.setPublisherId(commentIn.getPublisherId());
			commentOut.setPublisherTypeId(commentIn.getPublisherType().getId());
			commentOut.setPublisherTypeName(commentIn.getPublisherType().getName());
			
			String publisherTypeName = commentIn.getPublisherType().getName();
			if (publisherTypeName.equals("USER")) {
				try {
					UserDbModel user = userRepo.findById(commentIn.getPublisherId()).get();
					commentOut.setPublisherName(user.getFirstName() + " " + user.getLastName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}
			// If agent
			if (publisherTypeName.equals("AGENT")) {
				try {
					AgentDbModel agent = agentRepo.findById(commentIn.getPublisherId()).get();
					commentOut.setPublisherName(agent.getName());
				} catch (Exception e) {
					System.out.println(e);
					return null;
				}
			}
			/*
			commentOut.setPublisherName(providerUtil.fetchPublisherName(commentIn.getPublisherType().getName(),
					commentIn.getPublisherId()));
			*/
			commentOut.setComment(commentIn.getComment());
			//commentOut.setDate(providerUtil.getXmlGregorianCalendar(commentIn.getUnixTimestamp()));
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(commentIn.getUnixTimestamp());
			try {
				commentOut.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			internalResponse.add(commentOut);
		}

		return new ResponseEntity<List<CorrespondenceResponseModel>>(internalResponse, HttpStatus.OK);
		/*
		if (!internalResponse.isAuthorized()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpCorrespondenceResponse>>(translator.translate(internalResponse), HttpStatus.OK);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/comments/{id}/approve")
	public ResponseEntity<?> approve(@RequestHeader("token") String token, @PathVariable("id") int id) {
		
		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);
		/*
		if (!tokenObj.isValid() || !tokenObj.getRoleName().equals("ADMIN")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		*/

		//response.setAuthorized(true);
		
		Optional<CommentDbModel> comment = commentRepo.findById(id);
		if (!comment.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		comment.get().setApproved(true);

		commentRepo.save(comment.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		/*
		SoapApproveCommentRequest internalRequest = new SoapApproveCommentRequest();
		internalRequest.setCommentId(id);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/comments/{id}/reject")
	public ResponseEntity<?> reject(@RequestHeader("token") String token, @PathVariable("id") int id) {
		/*
		SoapRejectCommentRequest internalRequest = new SoapRejectCommentRequest();
		internalRequest.setCommentId(id);
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);
		/*
		if (!tokenObj.isValid() || !tokenObj.getRoleName().equals("ADMIN")) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		*/
		//response.setAuthorized(true);
		
		Optional<CommentDbModel> comment = commentRepo.findById(id);
		if (!comment.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		commentRepo.delete(comment.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "schedule/ratings")
	public ResponseEntity<?> addRating(@RequestHeader("token") String token, @RequestBody AddRatingRequestModel request) {
		/*
		SoapAddRatingRequest internalRequest = new SoapAddRatingRequest();
		internalRequest.setComment(request.getComment());
		internalRequest.setRating(request.getRating());
		internalRequest.setReservationId(request.getReservationId());
		internalRequest.setToken(token);
		
		SoapResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapResponse response = new SoapResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);
		/*
		if (!token.isValid() || !token.getRoleName().equals("BASIC")) {
			response.setAuthorized(false);
			return response;
		}
		*/

		Optional<ReservationDbModel> reservation = reservationRepo.findById(request.getReservationId());
		
		if (tokenObj.getUserId() != reservation.get().getBundle().getUserId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		//response.setAuthorized(true);
		
		//Is it already rated
		if (reservation.get().getRating()!=null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		//Is the new rating valid
		if (request.getRating() > 5 || request.getRating()<1) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		reservation.get().setRating(request.getRating());
		reservationRepo.save(reservation.get());

		if(request.getComment()!=null && request.getComment().trim().length()>0) {
			
			CommentDbModel comment = new CommentDbModel();
			comment.setReservation(reservation.get());
			comment.setComment(request.getComment());
			//comment.setPublisherId(token.getUserId());
			comment.setPublisherId(reservation.get().getBundle().getUserId());
			comment.setPublisherType(reservation.get().getBundle().getPublisherType());
			comment.setUnixTimestamp(Calendar.getInstance().getTimeInMillis());

			commentRepo.save(comment);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		*/
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "schedule/ratings/pending")
	public ResponseEntity<List<ReservationResponseModel>> getPendingRating(@RequestHeader("token") String token) {
		/*
		SoapPendingRatingRequest internalRequest = new SoapPendingRatingRequest();
		internalRequest.setToken(token);
		
		SoapReservationsResponse internalResponse = scheduleServiceClient.send(internalRequest);
		*/
		//SoapReservationsResponse response = new SoapReservationsResponse();

		AuthenticationTokenParseResult tokenObj = parseAuthenticationToken(token);

		if (!tokenObj.isValid() || tokenObj.getUserId() == null /* || !tokenObj.getRoleName().equals("BASIC") */) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		//response.setAuthorized(true);
		
		List<ReservationResponseModel> internalResponse = new ArrayList<ReservationResponseModel>();
		
		List<BundleDbModel> bundles = bundleRepo.findByUserIdAndStateId(tokenObj.getUserId(), reservationStateRepo.findById(6).get().getId());

		for (BundleDbModel bundle : bundles) {
			for (ReservationDbModel reservationIn : bundle.getReservations()) {
				if (reservationIn.getRating() != null) {
					continue;
				}
				
				ReservationResponseModel reservationOut = new ReservationResponseModel();

				reservationOut.setReservationId(reservationIn.getId());
				reservationOut.setCarId(reservationIn.getCarId());
				reservationOut.setWarrantyIncluded(reservationIn.isWarrantyIncluded());
				reservationOut.setTotalPrice(reservationIn.getTotalPrice());
				reservationOut
						.setExtraCharges(reservationIn.getExtraCharges() == null ? 0 : reservationIn.getExtraCharges());

				CarPriceListDbModel carPricelist = carPriceListRepo
						.findByCarId(reservationIn.getCarId()).stream()
						.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp()))
						.findFirst().orElse(null);

				if (carPricelist != null) {
					reservationOut.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
					reservationOut.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
				}

				// fetch car
				CarDbModel car = carRepo.findById(reservationIn.getCarId()).get();
				/*
				SoapCarRequest soapCarRequest = new SoapCarRequest();
				soapCarRequest.setId(reservationIn.getCarId());
				SoapCarResponse soapCarResponse = carServiceClient.send(soapCarRequest);
				*/
				
				reservationOut.setCarClassId(car.getCarClass().getId());
				reservationOut.setCarClassName(car.getCarClass().getName());
				reservationOut.setLocationId(car.getLocation().getId());
				reservationOut.setLocationName(car.getLocation().getName());
				reservationOut.setModelId(car.getCarModel().getId());
				reservationOut.setModelName(car.getCarModel().getName());
				reservationOut.setManufacturerId(car.getCarModel().getCarManufacturer().getId());
				reservationOut.setManufacturerName(car.getCarModel().getCarManufacturer().getName());
				reservationOut.setFuelTypeName(car.getFuelType().getName());
				reservationOut.setFuelTypeId(car.getFuelType().getId());
				reservationOut.setTransmissionTypeName(car.getTransmissionType().getName());
				reservationOut.setTransmissionTypeId(car.getTransmissionType().getId());
				reservationOut.setMileage(car.getMileage());
				reservationOut.setChildSeats(car.getChildSeats());
				reservationOut.setPublisherId(car.getPublisherId());
				reservationOut.setPublisherTypeId(car.getPublisherType().getId());
				reservationOut.setPublisherTypeName(car.getPublisherType().getName());
				
				try {
					int sum = 0;
					int count = 0;
					List<ReservationDbModel> dbResponses = reservationRepo.findByCarId(reservationIn.getCarId());
					for (ReservationDbModel objectIn2 : dbResponses) {
						if (objectIn2.getRating() != null) {
							sum += objectIn2.getRating();
							count++;
						}
					}
					reservationOut.setRating(Math.round((float) sum / (float) count));
				} catch (Exception e) {
					System.out.println(e);
					continue;
				}
				
				//reservationOut.setRating(soapCarResponse.getCar().getRating());
				
				for (CarImageDbModel image : car.getImages()) {
					reservationOut.getImages().add(image.getId());
				}
				
				//reservationOut.getImage().addAll(soapCarResponse.getCar().getImage());
				//String publisherName = fetchPublisherName(soapCarResponse.getCar().getPublisherTypeName(), soapCarResponse.getCar().getPublisherId());
				//reservationOut.setPublisherName(publisherName);
				
				
				String publisherTypeName = car.getPublisherType().getName();
				if (publisherTypeName.equals("USER")) {
					try {
						UserDbModel user = userRepo.findById(car.getPublisherId()).get();
						reservationOut.setPublisherName(user.getFirstName() + " " + user.getLastName());
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				// If agent
				if (publisherTypeName.equals("AGENT")) {
					try {
						AgentDbModel agent = agentRepo.findById(car.getPublisherId()).get();
						reservationOut.setPublisherName(agent.getName());
					} catch (Exception e) {
						System.out.println(e);
						return null;
					}
				}
				
				//return reservationOut;

				internalResponse.add(reservationOut);
			}
		}

		return new ResponseEntity<List<ReservationResponseModel>>(internalResponse, HttpStatus.OK);
		/*
		if (!internalResponse.isSuccess()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<HttpReservationResponse>>(translator.translate(internalResponse), HttpStatus.OK);
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
}
