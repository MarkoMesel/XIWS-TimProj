package com.student.scheduleservice.internal.provider;

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

@Component("ScheduleProvider")
public class ScheduleProvider {

	private UnitOfWork unitOfWork;
	private ProviderUtil providerUtil;

	@Autowired
	public ScheduleProvider(UnitOfWork unitOfWork, ProviderUtil providerUtil) {
		super();
		this.unitOfWork = unitOfWork;
		this.providerUtil = providerUtil;
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
				.sorted((l1, l2) -> (l2.getUnixTimestamp()).compareTo(l1.getUnixTimestamp())).findFirst().orElse(null);

		if (carPricelist == null) {
			return response;
		}

		// TODO: check for unavailabilities

		// TODO: check for existing reservations
		
		//TODO: do this better
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
}
