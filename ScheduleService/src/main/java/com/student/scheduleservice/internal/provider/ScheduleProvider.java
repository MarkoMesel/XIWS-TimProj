package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.CommentDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.dal.UnavailabilityDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.soap.client.AgentServiceClient;
import com.student.scheduleservice.soap.client.UserServiceClient;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityRequest;
import com.student.scheduleservice.soap.contract.SoapCarAvailabilityResponse;
import com.student.scheduleservice.soap.contract.SoapCarPhysicalRequest;
import com.student.scheduleservice.soap.contract.SoapCarPhysicalResponse;
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsResponse;
import com.student.soap.agentservice.contract.SoapAgentByIdRequest;
import com.student.soap.agentservice.contract.SoapAgentByIdResponse;
import com.student.soap.userservice.contract.SoapGetResponse;
import com.student.soap.userservice.contract.SoapInternalGetUserRequest;

@Component("ScheduleProvider")
public class ScheduleProvider {

	private UnitOfWork unitOfWork;
	private UserServiceClient userServiceClient;
	private AgentServiceClient agentServiceClient;

	@Autowired
	public ScheduleProvider(UnitOfWork unitOfWork, UserServiceClient userServiceClient,
			AgentServiceClient agentServiceClient) {
		super();
		this.unitOfWork = unitOfWork;
		this.userServiceClient = userServiceClient;
		this.agentServiceClient = agentServiceClient;
	}

	public SoapCarRatingsAndCommentsResponse getCarRatingsAndComments(int id) {
		SoapCarRatingsAndCommentsResponse response = new SoapCarRatingsAndCommentsResponse();
		response.setComments(new SoapCarRatingsAndCommentsResponse.Comments());

		unitOfWork.getReservationRepo().findByCarId(id).forEach(reservationIn -> {
			SoapCarRatingsAndCommentsResponse.Comments.Comment ratingAndComment = new SoapCarRatingsAndCommentsResponse.Comments.Comment();
			List<CommentDbModel> lista = unitOfWork.getCommentRepo().findByReservationId(reservationIn.getId());
			// SoapGetResponse getUser =
			// userServiceClient.getUser(lista.get(0).getPublisherId());
			SoapInternalGetUserRequest userRequest = new SoapInternalGetUserRequest();
			userRequest.setId(lista.get(0).getPublisherId());
			SoapGetResponse getUser = userServiceClient.send(userRequest);

			ratingAndComment.setUserId(lista.get(0).getId());
			ratingAndComment.setUserName(getUser.getFirstName() + " " + getUser.getLastName());
			ratingAndComment.setComment(lista.get(0).getComment());
			ratingAndComment.setRating(reservationIn.getRating());
			ratingAndComment.setReplies(new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies());

			for (int i = 1; i < lista.size(); i++) {
				SoapAgentByIdRequest getAgent = new SoapAgentByIdRequest();
				getAgent.setAgentId(lista.get(i).getPublisherId());
				SoapAgentByIdResponse agent = agentServiceClient.send(getAgent);
				SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
				reply.setPublisherName(agent.getName());
				reply.setComment(lista.get(i).getComment());
				reply.setPublisherId(lista.get(i).getPublisherId());
				reply.setPublisherTypeId(lista.get(i).getPublisherType().getId());
				ratingAndComment.getReplies().getReply().add(reply);
			}
			response.getComments().getComment().add(ratingAndComment);

		});

		response.setSuccess(true);
		return response;
	}

	public InternalCarRatingResponse getCarRating(int id) {
		InternalCarRatingResponse response = new InternalCarRatingResponse();
		int sum = 0;
		int count = 0;
		List<ReservationDbModel> dbResponses = unitOfWork.getReservationRepo().findByCarId(id);
		for (ReservationDbModel objectIn : dbResponses) {
			if (objectIn.getRating() != null) {
				sum += objectIn.getRating();
				count++;
			}
		}
		response.setRating(Math.round((float) sum / (float) count));
		response.setSuccess(true);
		return response;
	}

	public InternalCarPriceResponse getCarPrice(InternalCarPriceRequest request) {
		InternalCarPriceResponse response = new InternalCarPriceResponse();
		CarPriceListDbModel carPricelist = unitOfWork.getCarPriceListRepo().findByCarId(request.getCarId()).stream()
				.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		if (carPricelist == null) {
			return response;
		}
		
		// long currentTime = Instant.now().getEpochSecond();
		List<PriceDbModel> prices = carPricelist.getPriceList().getPrices().stream()
				.filter(price -> price.getStartDate().compareTo(request.getStartDate()) >= 0
						&& price.getEndDate().compareTo(request.getEndDate()) >= 0)
				.collect(Collectors.toList());

		int price = 0;
		for (PriceDbModel dailyPrice : prices) {
			price += dailyPrice.getPrice();
		}

		int discount = 0;
		response.setCollisionWarranty(carPricelist.getPriceList().getWarrantyPrice());
		if (request.getStartDate().compareTo(request.getEndDate()) >= 30) {
			discount = price * carPricelist.getPriceList().getDiscountPercentage() / 100;
		}
		response.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
		response.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
		response.setPrice(price);
		response.setTotalPrice(price - discount);
		response.setDiscount(discount);
		response.setSuccess(true);
		return response;
	}

	public SoapCarAvailabilityResponse getCarAvailability(SoapCarAvailabilityRequest request) {
		SoapCarAvailabilityResponse response = new SoapCarAvailabilityResponse();

		// posmatraj aktivan cenovnik
		CarPriceListDbModel carPriceList = unitOfWork.getCarPriceListRepo().findByCarId(request.getId()).stream()
				.sorted((l1, l2) -> ((BigInteger) l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst()
				.orElse(null);

		List<PriceDbModel> prices = carPriceList.getPriceList().getPrices().stream().filter(
				price -> price.getStartDate().compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& price.getEndDate().compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0)
				.collect(Collectors.toList());

		if (prices.isEmpty()) {
			response.setSuccess(false);
			return response;
		}

		// proveri da li se vozilo nalazi u unavailability tabeli
		if (unitOfWork.getUnavailabilityRepo().findByCarId(request.getId()) != null) {
			response.setSuccess(false);
			return response;
		}
		// treca provera sa rezervacijama
		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(request.getId()).stream()
				.filter(reservation -> reservation.getStartDate()
						.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& reservation.getStartDate()
								.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0
						|| reservation.getStartDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) <= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0
						|| reservation.getEndDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0)
				.collect(Collectors.toList());

		if (!reservations.isEmpty()) {
			response.setSuccess(false);
			return response;
		}

		response.setSuccess(true);
		return response;
	}

	public SoapCarPhysicalResponse getCarPhysical(SoapCarPhysicalRequest request) {
		// TODO Auto-generated method stub
		SoapCarPhysicalResponse response = new SoapCarPhysicalResponse();
		// AuthenticationTokenParseResult token =
		// jwtUtil.parseAuthenticationToken(request.getToken());

		// proveri token
		// ako je rezervacija potvrdjena, ne moze da rezervise
		// pending rezervacije se automatski odbijaju

		// provera da li agentov date ima preklapanja sa nekom od rezervacija

		List<ReservationDbModel> reservations = unitOfWork.getReservationRepo().findByCarId(request.getCarId()).stream()
				.filter(reservation -> reservation.getStartDate()
						.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
						&& reservation.getStartDate()
								.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0
						|| reservation.getStartDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) <= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) >= 0
						|| reservation.getEndDate()
								.compareTo(request.getStartDate().toGregorianCalendar().getTime()) >= 0
								&& reservation.getEndDate()
										.compareTo(request.getEndDate().toGregorianCalendar().getTime()) <= 0)
				.collect(Collectors.toList());

		for (ReservationDbModel reservation : reservations) {
			response.setTest(reservation.getBundle().getState().getName());
			if (reservation.getBundle().getState().getName().equals("RESERVATION_PAID")) {
				response.setSuccess(false);
				return response;
			}
		}
		// u protivnom, ako ne postoji preklapanje sa rezervacijama, slobodan je da
		// nastavi
		UnavailabilityDbModel unavaible = new UnavailabilityDbModel();
		unavaible.setCarId(request.getCarId());
		unavaible.setStartDate(request.getStartDate().toGregorianCalendar().getTime());
		unavaible.setEndDate(request.getEndDate().toGregorianCalendar().getTime());
		unitOfWork.getUnavailabilityRepo().save(unavaible);
		response.setSuccess(true);
		return response;
	}
}
