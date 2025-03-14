package com.wipro.training.bank.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.bank.dao.AccountDetailsDTO;
import com.wipro.training.bank.dao.RegisterRequest;
import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.service.AccountService;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 4:33:11 pm
Project: smart-bank
 */

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins="http://localhost:4200")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	/*POST--> http://localhost:8089/api/account/open
	 
		{
		    "accountId": "SB005",
		    "personalDetails": {
		        "title": "Mrs",
		        "firstName": "Pooja",
		        "middleName": "Singh",
		        "lastName": "Chauhan",
		        "fatherName": "Arvind Singh",
		        "mobile": "9876012345",
		        "email": "pooja.chauhan@gmail.com",
		        "adhar": "112233445566",
		        "dob": "1990-08-15"
		    },
		    "address": {
		        "addressLine1": "Sector 62",
		        "addressLine2": "Near City Plaza",
		        "landmark": "Opposite Green Park",
		        "state": "Uttar Pradesh",
		        "city": "Noida",
		        "pincode": "201301"
		    }
		}
			 */
	@PostMapping("/open")
	public Account openAccount(@RequestBody Account account) {
		logger.info("Opening account for user");
		return accountService.openAccount(account);
	}
	/*
	 * POST--> http://localhost:8089/api/account/register
	 * 	{
		    "accountNumber": "1234889519",
		    "userId": "Pooja07",
		    "loginPassword": "Password123",
		     "confirmLoginPassword": "Password123",
		    "transactionPassword": "TransPass123",
		     "confirmTransactionPassword": "TransPass123"
		}

	 */
	@PostMapping("/register")
	public Account registerInternetBanking(@RequestBody RegisterRequest request) {
		logger.info("Registering internet banking for account number: {}", request.getAccountNumber());
		return accountService.registerInternetBanking(request);
	}

	/* 
		GET--> http://localhost:8089/api/account/1234889519
	 */
	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable String accountNumber) {
		AccountDetailsDTO dto = accountService.getAccountDetails(accountNumber);

		if (dto != null) {
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null);  // Or return a message in case the account is not found
		}

	}

	//SWAGGER --> http://localhost:8089/swagger-ui/index.html#/

	//GET ---> http://localhost:8089/api/account/summary/1234889519
	@GetMapping("/summary/{accountNumber}")
	public ResponseEntity<Map<String, Object>> getAccountSummary(@PathVariable String accountNumber) {
		logger.info("Request received to get account summary for account number: {}", accountNumber);

		try {
			Map<String, Object> accountSummary = accountService.getAccountSummary(accountNumber);
			logger.info("Retrieved account summary for account number: {}", accountNumber);
			return ResponseEntity.ok(accountSummary);
		} catch (ResourceNotFoundException e) {
			logger.error("Error retrieving account summary for account number: {} - {}", accountNumber, e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}

