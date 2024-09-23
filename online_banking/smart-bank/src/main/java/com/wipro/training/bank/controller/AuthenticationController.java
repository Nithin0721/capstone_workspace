package com.wipro.training.bank.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.bank.dao.LoginRequest;
import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.service.AuthenticationService;

/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:05:08 pm
Project: smart-bank
 */

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/*POST--> http://localhost:8089/api/account/login
		{
			"userId":"Pooja07",
			"loginPassword":"Password123"
		}

	 */
	@PostMapping("/login")
	public Account login(@RequestBody LoginRequest loginRequest) {
		logger.info("Attempting login for user ID: {}", loginRequest.getUserId());
		return authenticationService.login(loginRequest);
	}

	/* 
	    http://localhost:8089/api/account/setNewPassword
		  {
			    "userId": "Pooja07",
			    "oldPassword": "password123",
			    "newPassword": "newPassword123",
			    "confirmNewPassword": "newPassword123"
			}
	 */
	@PostMapping("/setNewPassword")
	public ResponseEntity<String> setNewPassword(@RequestBody Map<String, String> passwordDetails) {
		String userId = passwordDetails.get("userId");
		String oldPassword = passwordDetails.get("oldPassword");
		String newPassword = passwordDetails.get("newPassword");
		String confirmNewPassword = passwordDetails.get("confirmNewPassword");

		try {
			authenticationService.setNewPassword(userId, oldPassword, newPassword, confirmNewPassword);
			return ResponseEntity.ok("Password updated successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
		}
	}
}
