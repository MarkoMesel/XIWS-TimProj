package com.student.scheduleservice.internal.translator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.student.scheduleservice.http.contract.HttpCommentResponse;
import com.student.scheduleservice.http.contract.HttpNamedObjectResponse;
import com.student.scheduleservice.internal.contract.InternalCarPriceRequest;
import com.student.scheduleservice.internal.contract.InternalCarPriceResponse;
import com.student.scheduleservice.internal.contract.InternalCarRatingResponse;
import com.student.scheduleservice.internal.contract.InternalCommentsResponse;
import com.student.scheduleservice.internal.contract.InternalNamedObjectsResponse;
import com.student.scheduleservice.soap.contract.SoapCarPriceRequest;
import com.student.scheduleservice.soap.contract.SoapCarPriceResponse;
import com.student.scheduleservice.soap.contract.SoapCarRatingResponse;
import com.student.scheduleservice.soap.contract.SoapNamedObjectsResponse;
import com.student.scheduleservice.soap.contract.SoapNamedObjectsResponse.NamedObjects;

@Component("Translator")
public class Translator {
	public List<HttpNamedObjectResponse> httpTranslate(InternalNamedObjectsResponse input) {
		List<HttpNamedObjectResponse> output = new ArrayList<>();

		for (InternalNamedObjectsResponse.NamedObject objectIn : input.getObjects()) {
			HttpNamedObjectResponse objectOut = new HttpNamedObjectResponse();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.add(objectOut);
		}
		return output;
	}

	public SoapCarRatingResponse soapTranslate(InternalCarRatingResponse input) {
		SoapCarRatingResponse output = new SoapCarRatingResponse();
		output.setSuccess(input.isSuccess());
		output.setRating(input.getRating());
		return output;
	}

	public SoapNamedObjectsResponse soapTranslate(InternalNamedObjectsResponse input) {
		SoapNamedObjectsResponse output = new SoapNamedObjectsResponse();
		output.setNamedObjects(new NamedObjects());
		output.setSuccess(input.isSuccess());

		for (InternalNamedObjectsResponse.NamedObject objectIn : input.getObjects()) {
			SoapNamedObjectsResponse.NamedObjects.NamedObject objectOut = new SoapNamedObjectsResponse.NamedObjects.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.getNamedObjects().getNamedObject().add(objectOut);
		}

		return output;
	}

	public List<HttpCommentResponse> httpTranslate(InternalCommentsResponse input) {
		List<HttpCommentResponse> output = new ArrayList<>();

		for (InternalCommentsResponse.Comment objectIn : input.getComments()) {
			HttpCommentResponse objectOut = new HttpCommentResponse();
			objectOut.setComment(objectIn.getComment());
			objectOut.setRating(objectIn.getRating());
			objectOut.setReply(objectIn.getReply());
			output.add(objectOut);
		}

		return output;
	}

	public InternalCarPriceRequest translate(SoapCarPriceRequest input) {
		InternalCarPriceRequest output = new InternalCarPriceRequest();
		output.setCarId(input.getId());
		output.setStartDate(input.getStart().toGregorianCalendar().getTime());
		output.setEndDate(input.getEnd().toGregorianCalendar().getTime());
		return output;
	}
	
	public SoapCarPriceResponse soapTranslate(InternalCarPriceResponse input) {
		SoapCarPriceResponse output = new SoapCarPriceResponse();
		output.setSuccess(input.isSuccess());
		output.setDiscount(BigInteger.valueOf(input.getDiscount()));
		output.setMileagePenalty(BigInteger.valueOf(input.getMileagePenalty()));
		output.setMileageThreshold(BigInteger.valueOf(input.getMileageThreshold()));
		output.setPrice(BigInteger.valueOf(input.getPrice()));
		output.setTotalPrice(BigInteger.valueOf(input.getTotalPrice()));
		output.setCollisionWarranty(BigInteger.valueOf(input.getCollisionWarranty()));
		return output;
	}
}
