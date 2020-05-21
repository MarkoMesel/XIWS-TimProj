package com.student.scheduleservice.internal.provider;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.student.scheduleservice.data.dal.CarPriceListDbModel;
import com.student.scheduleservice.data.dal.PriceDbModel;
import com.student.scheduleservice.data.dal.ReservationDbModel;
import com.student.scheduleservice.data.repo.UnitOfWork;
import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.internal.contract.InternalCommentsResponse;
import com.student.scheduleservice.internal.contract.InternalNamedObjectsResponse;

@Component("ScheduleProvider")
public class ScheduleProvider {

	private UnitOfWork unitOfWork;
	
	@Autowired
	public ScheduleProvider(UnitOfWork unitOfWork) {
		super();
		this.unitOfWork = unitOfWork;
	}

	public InternalNamedObjectsResponse getAllLocations() {
		InternalNamedObjectsResponse response = new InternalNamedObjectsResponse();

		unitOfWork.getLocationRepo().findAll().forEach(objectIn -> {
			InternalNamedObjectsResponse.NamedObject objectOut = new InternalNamedObjectsResponse.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			response.addObject(objectOut);
		});

		response.setSuccess(true);
		return response;
	}

	public InternalCommentsResponse getCarComments(int id) {
		InternalCommentsResponse response = new InternalCommentsResponse();

		//TODO: InternalCommentsResponse - fetch comments by reservation
		/*
		reservationRepo.findByCarId(id).forEach(objectIn -> {
			InternalCommentsResponse.Comment objectOut = new InternalCommentsResponse.Comment();
			if(objectIn.isUserCommentApproved() && objectIn.getUserComment()!=null){
				objectOut.setComment(objectIn.getUserComment());
				objectOut.setRating(objectIn.getRating());
				objectOut.setReply(objectIn.getAgentReply());
				response.getComments().add(objectOut);
			}
		});
		*/

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
		CarPriceListDbModel carPricelist = unitOfWork.getCarPriceListRepo()
			.findByCarId(request.getCarId())
			.stream()
			.sorted((l1, l2) -> ((BigInteger)l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp()))
			.findFirst().orElse(null);
		
		//long currentTime = Instant.now().getEpochSecond();
		List<PriceDbModel> prices= carPricelist.getPriceList().getPrices().stream()
		.filter(price -> price.getDate().compareTo(request.getStartDate())>=0 && price.getDate().compareTo(request.getEndDate())<=0)
		.collect(Collectors.toList());
		
		int price=0;
		for(PriceDbModel dailyPrice:prices) {
			price+=dailyPrice.getPrice();
		}
		
		int discount=0;
		response.setCollisionWarranty(carPricelist.getPriceList().getWarrantyPrice());
		if(request.getStartDate().compareTo(request.getEndDate())>=30)
		{
			discount = price*carPricelist.getPriceList().getDiscountPercentage()/100;			
		}
		response.setMileagePenalty(carPricelist.getPriceList().getMileagePenalty());
		response.setMileageThreshold(carPricelist.getPriceList().getMileageThreshold());
		response.setPrice(price);
		response.setTotalPrice(price-discount);
		response.setDiscount(discount);
		response.setSuccess(true);
		return response;
	}
}
