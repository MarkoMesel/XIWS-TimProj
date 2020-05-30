package com.student.access.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.student.internal.provider.CarProvider;
import com.student.internal.translator.Translator;
import com.student.soap.contract.SoapAddCarClassRequest;
import com.student.soap.contract.SoapAddCarModelRequest;
import com.student.soap.contract.SoapAddFuelTypeRequest;
import com.student.soap.contract.SoapAddManufacturerRequest;
import com.student.soap.contract.SoapAddTransmissionTypeRequest;
import com.student.soap.contract.SoapAllCarModelsRequest;
import com.student.soap.contract.SoapCarClassesRequest;
import com.student.soap.contract.SoapCarManufacturersRequest;
import com.student.soap.contract.SoapCarModelsByManufacturerRequest;
import com.student.soap.contract.SoapCarModelsResponse;
import com.student.soap.contract.SoapCarRequest;
import com.student.soap.contract.SoapCarResponse;
import com.student.soap.contract.SoapDeleteCarClassRequest;
import com.student.soap.contract.SoapDeleteCarModelRequest;
import com.student.soap.contract.SoapDeleteFuelTypeRequest;
import com.student.soap.contract.SoapDeleteImageRequest;
import com.student.soap.contract.SoapDeleteManufacturerRequest;
import com.student.soap.contract.SoapDeleteTransmissionTypeRequest;
import com.student.soap.contract.SoapFuelTypesRequest;
import com.student.soap.contract.SoapGetImageRequest;
import com.student.soap.contract.SoapGetImageResponse;
import com.student.soap.contract.SoapNamedObjectsResponse;
import com.student.soap.contract.SoapPostImageRequest;
import com.student.soap.contract.SoapPostImageResponse;
import com.student.soap.contract.SoapResponse;
import com.student.soap.contract.SoapTransmissionTypesRequest;

@Endpoint
public class CarModelEndpoint {
	private static final String NAMESPACE_URI = "http://www.student.com/soap/contract";

	private CarProvider carProvider;
	private Translator translator;

	@Autowired
	public CarModelEndpoint(CarProvider carProvider, Translator translator) {
		this.carProvider = carProvider;
		this.translator = translator;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarManufacturersRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarManufacturers(@RequestPayload SoapCarManufacturersRequest request) {
		return translator.soapTranslate(carProvider.getCarManufacturers());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAllCarModelsRequest")
	@ResponsePayload
	public SoapCarModelsResponse getAllCarModels(@RequestPayload SoapAllCarModelsRequest request) {
		return translator.soapTranslate(carProvider.getAllCarModels());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarModelsByManufacturerRequest")
	@ResponsePayload
	public SoapCarModelsResponse getCarModelsByManufacturerId(@RequestPayload SoapCarModelsByManufacturerRequest request) {
		return translator.soapTranslate(carProvider.getCarModelsByManufacturerId(request.getManufacturerId()));
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapFuelTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getFuelTypes(@RequestPayload SoapFuelTypesRequest request) {
		return translator.soapTranslate(carProvider.getFuelTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapTransmissionTypesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getTransmissionTypes(@RequestPayload SoapTransmissionTypesRequest request) {
		return translator.soapTranslate(carProvider.getTransmissionTypes());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarClassesRequest")
	@ResponsePayload
	public SoapNamedObjectsResponse getCarClasses(@RequestPayload SoapCarClassesRequest request) {
		return translator.soapTranslate(carProvider.getCarClasses());
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapCarRequest")
	@ResponsePayload
	public SoapCarResponse getCarClasses(@RequestPayload SoapCarRequest request) {
		return carProvider.getCar(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapGetImageRequest")
	@ResponsePayload
	public SoapGetImageResponse getCarImage(@RequestPayload SoapGetImageRequest request) {
		return carProvider.getCarImage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapPostImageRequest")
	@ResponsePayload
	public SoapPostImageResponse postCarImage(@RequestPayload SoapPostImageRequest request) {
		return carProvider.postCarImage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteImageRequest")
	@ResponsePayload
	public SoapResponse deleteImage(@RequestPayload SoapDeleteImageRequest request) {
		return carProvider.deleteCarImage(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarClassRequest")
	@ResponsePayload
	public SoapResponse addCarClass(@RequestPayload SoapAddCarClassRequest request) {
		return carProvider.addCarClass(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarClassRequest")
	@ResponsePayload
	public SoapResponse deleteCarClass(@RequestPayload SoapDeleteCarClassRequest request) {
		return carProvider.deleteCarClass(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddManufacturerRequest")
	@ResponsePayload
	public SoapResponse addCarManufacturer(@RequestPayload SoapAddManufacturerRequest request) {
		return carProvider.addManufacturer(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteManufacturerRequest")
	@ResponsePayload
	public SoapResponse deleteCarManufacturer(@RequestPayload SoapDeleteManufacturerRequest request) {
		return carProvider.deleteManufacturer(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddCarModelRequest")
	@ResponsePayload
	public SoapResponse addCarModel(@RequestPayload SoapAddCarModelRequest request) {
		return carProvider.addCarModel(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteCarModelRequest")
	@ResponsePayload
	public SoapResponse deleteCarModel(@RequestPayload SoapDeleteCarModelRequest request) {
		return carProvider.deleteCarModel(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddFuelTypeRequest")
	@ResponsePayload
	public SoapResponse addFuelType(@RequestPayload SoapAddFuelTypeRequest request) {
		return carProvider.addFuelType(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteFuelTypeRequest")
	@ResponsePayload
	public SoapResponse deleteFuelType(@RequestPayload SoapDeleteFuelTypeRequest request) {
		return carProvider.deleteFuelType(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapAddTransmissionTypeRequest")
	@ResponsePayload
	public SoapResponse addTransmissionType(@RequestPayload SoapAddTransmissionTypeRequest request) {
		return carProvider.addTransmissionType(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "soapDeleteTransmissionTypeRequest")
	@ResponsePayload
	public SoapResponse deleteTransmissionType(@RequestPayload SoapDeleteTransmissionTypeRequest request) {
		return carProvider.deleteTransmissionType(request);
	}
}
