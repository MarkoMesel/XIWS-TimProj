package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.CommentDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.soap.client.AgentServiceClient;
import com.student.scheduleservice.soap.client.UserServiceClient;
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

		// long currentTime = Instant.now().getEpochSecond();
		List<PriceDbModel> prices = carPricelist.getPriceList().getPrices().stream()
				.filter(price -> price.getDate().compareTo(request.getStartDate()) >= 0
						&& price.getDate().compareTo(request.getEndDate()) <= 0)
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
}
