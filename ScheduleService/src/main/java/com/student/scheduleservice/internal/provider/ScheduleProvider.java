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
import com.student.scheduleservice.soap.contract.SoapCarRatingsAndCommentsResponse;

@Component("ScheduleProvider")
public class ScheduleProvider {

	private UnitOfWork unitOfWork;

	@Autowired
	public ScheduleProvider(UnitOfWork unitOfWork) {
		super();
		this.unitOfWork = unitOfWork;
	}

	public SoapCarRatingsAndCommentsResponse getCarRatingsAndComments(int id) {
		SoapCarRatingsAndCommentsResponse response = new SoapCarRatingsAndCommentsResponse();
		response.setComments(new SoapCarRatingsAndCommentsResponse.Comments());
		// TODO: fetch comments, ratings and replies by car.
		// group by >>reservation<<
		// Comments with oldest timestamp are the original comments, while all other
		// comments of the same reservation are replies to the users comment
		// Test data is in data.sql
		// FOR TESTING:

		unitOfWork.getReservationRepo().findByCarId(id).forEach(reservationIn -> {
			SoapCarRatingsAndCommentsResponse.Comments.Comment ratingAndComm = new SoapCarRatingsAndCommentsResponse.Comments.Comment();
			ratingAndComm.setReplies(new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies());
			// privremena lista u kojoj je nulti clan komentar, a svi ostali su reply
			List<CommentDbModel> lista = new ArrayList<CommentDbModel>();
			lista = unitOfWork.getCommentRepo().findByReservationId(reservationIn.getId());

			ratingAndComm.setComment(lista.get(0).getComment());
			ratingAndComm.setRating(reservationIn.getRating());
			
			for (int i = 1; i < lista.size(); i++) {
				SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
				reply.setComment(lista.get(i).getComment());
				ratingAndComm.getReplies().getReply().add(reply);
			}
			response.getComments().getComment().add(ratingAndComm); //// gotov response

		});

		final GregorianCalendar now = new GregorianCalendar();
		XMLGregorianCalendar currentDateTime;
		try {
			currentDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(now);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return response;
		}

		{
			SoapCarRatingsAndCommentsResponse.Comments.Comment ratingAndComment = new SoapCarRatingsAndCommentsResponse.Comments.Comment();
			ratingAndComment.setComment("Test Comment1 : Great car");
			ratingAndComment.setDate(currentDateTime);
			ratingAndComment.setRating(5);
			ratingAndComment.setUserId(1);
			ratingAndComment.setUserName("Test Comment1 :Pera Peric"); // Fetch first name and last name from user
																		// service

			ratingAndComment.setReplies(new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies());
			SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply1 = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
			reply1.setComment("Test Comment1 : Thank you!");
			reply1.setDate(currentDateTime);
			reply1.setPublisherId(2);
			reply1.setPublisherName("Test Comment1 : Super Cool agency"); // Fetch agent name from agent service
			reply1.setPublisherTypeId(2);
			reply1.setPublisherTypeName("AGENT");

			SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply2 = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
			reply2.setComment("Test Comment1 : You are welcome!");
			reply2.setDate(currentDateTime);
			reply2.setPublisherId(1);
			reply2.setPublisherName("Test Comment1 : Pera Peric"); // Fetch first name and last name from user service
			reply2.setPublisherTypeId(1);
			reply2.setPublisherTypeName("AGENT");

			ratingAndComment.getReplies().getReply().add(reply1);
			ratingAndComment.getReplies().getReply().add(reply2);
			response.getComments().getComment().add(ratingAndComment);
		}

		{
			SoapCarRatingsAndCommentsResponse.Comments.Comment ratingAndComment = new SoapCarRatingsAndCommentsResponse.Comments.Comment();
			ratingAndComment.setComment("Test Comment2 : Awful car");
			ratingAndComment.setDate(currentDateTime);
			ratingAndComment.setRating(1);
			ratingAndComment.setUserId(23);
			ratingAndComment.setUserName("Test Comment2 : Cile Mile"); // Fetch first name and last name from user
																		// service

			ratingAndComment.setReplies(new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies());
			SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply1 = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
			reply1.setComment("Test Comment2 : Sorry to hear that!");
			reply1.setDate(currentDateTime);
			reply1.setPublisherId(45);
			reply1.setPublisherName("Test Comment2 : Super Cool agency"); // Fetch agent name from agent service
			reply1.setPublisherTypeId(2);
			reply1.setPublisherTypeName("AGENT");

			SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply reply2 = new SoapCarRatingsAndCommentsResponse.Comments.Comment.Replies.Reply();
			reply2.setComment("Test Comment2 : Hopefully better next time.");
			reply2.setDate(currentDateTime);
			reply2.setPublisherId(23);
			reply2.setPublisherName("Test Comment2 : Cile Mile"); // Fetch first name and last name from user service
			reply2.setPublisherTypeId(1);
			reply2.setPublisherTypeName("BASIC");

			ratingAndComment.getReplies().getReply().add(reply1);
			ratingAndComment.getReplies().getReply().add(reply2);
			response.getComments().getComment().add(ratingAndComment);
		}

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
