package com.student.scheduleservice.internal.translator;

import org.springframework.stereotype.Component;

import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarPriceResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingResponse;

@Component("Translator")
public class Translator {
	public SoapCarRatingResponse soapTranslate(InternalCarRatingResponse input) {
		SoapCarRatingResponse output = new SoapCarRatingResponse();
		output.setSuccess(input.isSuccess());
		output.setRating(input.getRating());
		return output;
	}

	public InternalCarPriceRequest translate(SoapCarPriceRequest input) {
		InternalCarPriceRequest output = new InternalCarPriceRequest();
		output.setCarId(input.getId());
		output.setStartDate(input.getStartDate().toGregorianCalendar().getTime());
		output.setEndDate(input.getEndDate().toGregorianCalendar().getTime());
		return output;
	}
	
	public SoapCarPriceResponse soapTranslate(InternalCarPriceResponse input) {
		SoapCarPriceResponse output = new SoapCarPriceResponse();
		output.setSuccess(input.isSuccess());
		output.setDiscount(input.getDiscount());
		output.setMileagePenalty(input.getMileagePenalty());
		output.setMileageThreshold(input.getMileageThreshold());
		output.setPrice(input.getPrice());
		output.setTotalPrice(input.getTotalPrice());
		output.setCollisionWarranty(input.getCollisionWarranty());
		return output;
	}
}
