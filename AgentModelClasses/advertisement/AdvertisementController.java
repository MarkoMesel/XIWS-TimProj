package com.siteproj0.demo.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdvertisementController {

	@Autowired
	AdvertisementRepo AdRepo;
	
	@Autowired
	AdvertisementImageRepo AdImageRepo;
}
