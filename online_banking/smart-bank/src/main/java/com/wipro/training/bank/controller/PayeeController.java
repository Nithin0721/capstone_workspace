package com.wipro.training.bank.controller;

import java.util.List;
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

import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Payee;
import com.wipro.training.bank.service.PayeeService;
/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:03:45 pm
Project: smart-bank
 */

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins="http://localhost:4200")
public class PayeeController {

    private static final Logger logger = LoggerFactory.getLogger(PayeeController.class);
    private final PayeeService payeeService;

    public PayeeController(PayeeService payeeService) {
        this.payeeService = payeeService;
    }

     /* POST -->  http://localhost:8089/api/account/1234889519/payee
		 {
		  "payee": {
		    "name": "Suman",
		    "accountNumber": "1754142927",
		    "confirmAccountNumber": "1754142927",
		    "nickname": "chika"
		  }
		}
      */
    @PostMapping("/{accountNumber}/payee")
    public ResponseEntity<Account> addPayee(
            @PathVariable String accountNumber,
            @RequestBody Map<String, Object> requestBody) {

        logger.info("Request received to add a payee for account number: {}", accountNumber);

        Map<String, String> payeeData = (Map<String, String>) requestBody.get("payee");
        if (payeeData == null) {
            logger.error("Invalid payee data provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Extract payee details from the request
        Payee newPayee = new Payee();
        newPayee.setBeneficiaryName(payeeData.get("name"));
        newPayee.setAccountNumber(payeeData.get("accountNumber"));
        newPayee.setNickname(payeeData.getOrDefault("nickname", ""));

        String confirmAccountNumber = payeeData.get("confirmAccountNumber");

        try {
            Account updatedAccount = payeeService.addPayee(accountNumber, newPayee, confirmAccountNumber);
            logger.info("Payee added successfully to account number: {}", accountNumber);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding payee to account number: {} - {}", accountNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
      GET--> http://localhost:8089/api/account/1234889519/payees
     */
    @GetMapping("/{accountNumber}/payees")
    public ResponseEntity<List<Payee>> getPayees(@PathVariable String accountNumber) {
        logger.info("Request received to get payees for account number: {}", accountNumber);

        try {
            List<Payee> payees = payeeService.getPayees(accountNumber);
            logger.info("Retrieved {} payees for account number: {}", payees.size(), accountNumber);
            return ResponseEntity.ok(payees);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving payees for account number: {} - {}", accountNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
