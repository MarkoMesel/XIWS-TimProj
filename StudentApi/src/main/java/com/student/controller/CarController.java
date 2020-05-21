package com.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.student.internal.translator.Translator;
import com.student.soap.client.CarServiceClient;

@Controller
public class CarController {
	private Translator translator;
	private CarServiceClient carServiceClient;

	@Autowired
	public CarController(CarServiceClient carServiceClient, Translator translator) {
		this.carServiceClient = carServiceClient;
		this.translator = translator;
	}
	//TODO : car service http
}
