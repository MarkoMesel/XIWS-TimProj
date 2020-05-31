package com.student.internal.translator;

import org.springframework.stereotype.Component;

import com.student.internal.contract.InternalCarModelsResponse;
import com.student.internal.contract.InternalNamedObjectsResponse;
import com.student.soap.carservice.contract.NamedObject;
import com.student.soap.carservice.contract.SoapCarModelsResponse;
import com.student.soap.carservice.contract.SoapNamedObjectsResponse;

@Component("Translator")
public class Translator {	
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
	
	public SoapNamedObjectsResponse soapTranslate(InternalNamedObjectsResponse input)
	{
		SoapNamedObjectsResponse output = new SoapNamedObjectsResponse();
		output.setSuccess(input.isSuccess());
		
		if(!output.isSuccess()) {
			return output;
		}
		
		for(InternalNamedObjectsResponse.NamedObject objectIn: input.getObjects())
		{
			NamedObject objectOut = new NamedObject();
			objectOut.setId(objectIn.getId());
			objectOut.setName(objectIn.getName());
			output.getObject().add(objectOut);
		}
		
		return output;
	}
}
