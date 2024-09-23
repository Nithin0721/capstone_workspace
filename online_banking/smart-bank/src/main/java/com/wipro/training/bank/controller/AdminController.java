package com.wipro.training.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.bank.dao.AdminRequest;
import com.wipro.training.bank.exception.UnauthorizedException;
import com.wipro.training.bank.model.Admin;
import com.wipro.training.bank.service.AdminService;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 3:33:11 pm
Project: smart-bank
 */

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins="http://localhost:4200")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private final AdminService adminService;

	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASSWORD = "admin123";

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	/* POST --> http://localhost:8089/api/admin/credit
    {
    	  "accountNumber": "1234889519",
    	  "amount": 800.00,
    	  "username": "admin",
    	  "password": "admin123"
    	}*/
	@PostMapping("/credit")
	public Admin creditMoney(@RequestBody AdminRequest request) {

		logger.info("Admin attempting to credit money for account number: {}", request.getAccountNumber());
		if (authenticate(request.getUsername(), request.getPassword())) {
			return adminService.creditMoney(request);
		} else {
			throw new UnauthorizedException("Invalid admin credentials");
		}
	}

	/*POST --> http://localhost:8089/api/admin/debit
	{
		  "accountNumber": "1234889519",
		  "amount": 100.00,
		  "username": "admin",
		  "password": "admin123"
		}*/
	@PostMapping("/debit")
	public Admin debitMoney(@RequestBody AdminRequest request) {

		logger.info("Admin attempting to debit money for account number: {}", request.getAccountNumber());
		if (authenticate(request.getUsername(), request.getPassword())) {
			return adminService.debitMoney(request);
		} else {
			throw new UnauthorizedException("Invalid admin credentials");
		}
	}

	private boolean authenticate(String username, String password) {
		return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
	}
}
