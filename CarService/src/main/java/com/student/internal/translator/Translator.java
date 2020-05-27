package com.student.internal.translator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.student.http.contract.HttpAddCarRequest;
import com.student.http.contract.HttpCarModelResponse;
import com.student.http.contract.HttpCarResponse;
import com.student.http.contract.HttpNamedObjectResponse;
import com.student.internal.contract.InternalAddCarRequest;
import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalCarResponse;
import com.student.internal.contract.InternalCarsResponse;
import com.student.internal.contract.InternalImageResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.soap.contract.SoapCarModelsResponse;
import com.student.soap.contract.SoapNamedObjectsResponse;

@Component("Translator")
public class Translator {
	public InternalAddCarRequest translate (String token, HttpAddCarRequest input) {
		InternalAddCarRequest output = new InternalAddCarRequest();
		
		output.setToken(token);
		output.setAgentId(input.getAgentId());
		output.setCarClassId(input.getCarClassId());
		output.setChildSeats(input.getChildSeats());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setMileage(input.getMileage());
		output.setModelId(input.getModelId());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		
		return output;
	}
	public List<HttpCarResponse> httpTranslate(InternalCarsResponse input)
	{
		List<HttpCarResponse> output = new ArrayList<>();
		
		for(InternalCarsResponse.Car objectIn: input.getCars())
		{
			HttpCarResponse objectOut= new HttpCarResponse();
			
			objectOut.setId(objectIn.getId());
			objectOut.setModelId(objectIn.getModelId());
			objectOut.setModelName(objectIn.getModelName());
			objectOut.setManufacturerId(objectIn.getManufacturerId());
			objectOut.setManufacturerName(objectIn.getManufacturerName());
			objectOut.setFuelType(objectIn.getFuelType());
			objectOut.setFuelTypeId(objectIn.getFuelTypeId());
			objectOut.setTransmission(objectIn.getTransmission());
			objectOut.setTransmissionTypeId(objectIn.getTransmissionTypeId());
			objectOut.setCarClass(objectIn.getCarClass());
			objectOut.setCarClassId(objectIn.getCarClassId());
			objectOut.setPricePerDay(objectIn.getPricePerDay());
			objectOut.setCollisionWaranty(objectIn.isCollisionWaranty());
			objectOut.setMileage(objectIn.getMileage());
			objectOut.setMileageThreshold(objectIn.getMileageThreshold());
			objectOut.setMileagePenalty(objectIn.getMileagePenalty());
			objectOut.setChildSeats(objectIn.getChildSeats());
			objectOut.setImages(objectIn.getCarImages());
			objectOut.setCarRating(objectIn.getCarRating());
			objectOut.setAgentId(objectIn.getAgentId());
			objectOut.setAgentName(objectIn.getAgentName());
			output.add(objectOut);
		}
		return output;
	}
	
	public HttpCarResponse httpTranslate(InternalCarResponse input)
	{
		HttpCarResponse output = new HttpCarResponse();			
		output.setId(input.getId());
		output.setModelId(input.getModelId());
		output.setModelName(input.getModelName());
		output.setManufacturerId(input.getManufacturerId());
		output.setManufacturerName(input.getManufacturerName());
		output.setFuelType(input.getFuelType());
		output.setFuelTypeId(input.getFuelTypeId());
		output.setTransmission(input.getTransmission());
		output.setTransmissionTypeId(input.getTransmissionTypeId());
		output.setCarClass(input.getCarClass());
		output.setCarClassId(input.getCarClassId());
		output.setPricePerDay(input.getPricePerDay());
		output.setCollisionWaranty(input.isCollisionWaranty());
		output.setMileage(input.getMileage());
		output.setMileageThreshold(input.getMileageThreshold());
		output.setMileagePenalty(input.getMileagePenalty());
		output.setChildSeats(input.getChildSeats());
		output.setImages(input.getCarImages());
		output.setCarRating(input.getCarRating());
		output.setAgentId(input.getAgentId());
		output.setAgentName(input.getAgentName());
		
		return output;
	}
	
	public List<HttpCarModelResponse> httpTranslate(InternalCarModelsResponse input)
	{
		List<HttpCarModelResponse> output = new ArrayList<>();
		
		for(InternalCarModelsResponse.CarModel inputCarModel: input.getCarModels())
		{
			HttpCarModelResponse outputCarModel= new HttpCarModelResponse();
			outputCarModel.setManufacturerId(inputCarModel.getManufacturerId());
			outputCarModel.setManufacturerName(inputCarModel.getManufacturerName());
			outputCarModel.setModelId(inputCarModel.getModelId());
			outputCarModel.setModelName(inputCarModel.getModelName());
			output.add(outputCarModel);
		}
		return output;
	}
	
	public byte[] httpTranslate(InternalImageResponse input)
	{
		byte[] output;
		output = input.getImage();
		return output;
	}
	
	public SoapCarModelsResponse soapTranslate(InternalCarModelsResponse input)
	{
		SoapCarModelsResponse output = new SoapCarModelsResponse();
		
		output.setSuccess(input.isSuccess());
		if(!output.isSuccess()) {
			return output;
		}
		output.setCarModels(new SoapCarModelsResponse.CarModels());
		for(InternalCarModelsResponse.CarModel inputCarModel: input.getCarModels())
		{
			SoapCarModelsResponse.CarModels.CarModel outputCarModel= new SoapCarModelsResponse.CarModels.CarModel();
			outputCarModel.setManufacturerId(inputCarModel.getManufacturerId());
			outputCarModel.setManufacturerName(inputCarModel.getManufacturerName());
			outputCarModel.setModelId(inputCarModel.getModelId());
			outputCarModel.setModelName(inputCarModel.getModelName());
			output.getCarModels().getCarModel().add(outputCarModel);
		}
		
		return output;
	}
	
	public List<HttpNamedObjectResponse> httpTranslate(InternalNamedObjectsResponse input)
	{
		List<HttpNamedObjectResponse> output = new ArrayList<>();
		
		for(InternalNamedObjectsResponse.NamedObject objectIn: input.getObjects())
		{
			HttpNamedObjectResponse objectOut= new HttpNamedObjectResponse();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.add(objectOut);
		}
		return output;
	}
	
	public SoapNamedObjectsResponse soapTranslate(InternalNamedObjectsResponse input)
	{
		SoapNamedObjectsResponse output = new SoapNamedObjectsResponse();
		output.setSuccess(input.isSuccess());
		
		if(!output.isSuccess()) {
			return output;
		}
		
		output.setNamedObjects(new SoapNamedObjectsResponse.NamedObjects());
		
		for(InternalNamedObjectsResponse.NamedObject objectIn: input.getObjects())
		{
			SoapNamedObjectsResponse.NamedObjects.NamedObject objectOut = new SoapNamedObjectsResponse.NamedObjects.NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.getNamedObjects().getNamedObject().add(objectOut);
		}
		
		return output;
	}
}
