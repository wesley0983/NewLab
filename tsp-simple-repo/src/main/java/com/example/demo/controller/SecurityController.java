package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SecurityService;

@RestController
@RequestMapping(path = "/base64")
public class SecurityController {
	
	@Autowired
	SecurityService securityService;
	
	@GetMapping
	public String stringToBase64String(@RequestBody String inputString) {
		return securityService.stringToBase64String(inputString);
	}
	@PostMapping
	public String byteStringToBase64String(@RequestBody byte[] inputbyte) {
		return securityService.byteToBase64String(inputbyte);
	}
	@PutMapping
	public String Base64StringLength(@RequestBody String baseString) {
		securityService.Base64StringLength(baseString);
		return "eclipseConsole";
	}
	
	
	
	

}