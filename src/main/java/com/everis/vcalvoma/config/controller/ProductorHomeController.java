package com.everis.vcalvoma.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductorHomeController {
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
}
