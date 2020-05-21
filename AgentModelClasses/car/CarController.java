package com.siteproj0.demo.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CarController {

	@Autowired
	CarRepo repo;
	
}
