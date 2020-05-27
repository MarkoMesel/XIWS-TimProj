package com.siteproj0.demo.firm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FirmController {

	@Autowired
	FirmRepo repo;
}
