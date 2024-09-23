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

import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Transaction;
import com.wipro.training.bank.service.TransactionService;

/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:04:24 pm
Project: smart-bank
 */

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins="http://localhost:4200")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /*
     * Retrieves the list of transactions for a given account number.
     
      GET--> http://localhost:8085/api/account/1234889519/transactions
     */
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionDetails(@PathVariable String accountNumber) {
        logger.info("Request received to get transactions for account number: {}", accountNumber);

        try {
            List<Transaction> transactions = transactionService.getTransactionDetails(accountNumber);
            logger.info("Retrieved {} transactions for account number: {}", transactions.size(), accountNumber);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving transactions for account number: {} - {}", accountNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /*
     * Performs an NEFT transaction.
     
		POST-->	http://localhost:8085/api/account/neft
		
		{
			"fromAccount": "1754142927",
			"toAccount": "1234889519",
			"amount": 1500.00,
			"remark": "NEFT Transfer"
		}
     */
    @PostMapping("/neft")
    public ResponseEntity<Account> performNEFTTransaction(
            @RequestBody Map<String, Object> requestBody) {

        String fromAccountNumber = (String) requestBody.get("fromAccount");
        String toAccountNumber = (String) requestBody.get("toAccount");
        double amount = Double.parseDouble(requestBody.get("amount").toString());
        String remark = (String) requestBody.get("remark");

        logger.info("Request received to perform NEFT transaction from account number: {} to account number: {}", fromAccountNumber, toAccountNumber);

        try {
            Account updatedAccount = transactionService.performNEFTTransaction(fromAccountNumber, toAccountNumber, amount, remark);
            logger.info("NEFT transaction successful from account number: {} to account number: {}", fromAccountNumber, toAccountNumber);
            return ResponseEntity.ok(updatedAccount);
        } catch (ResourceNotFoundException e) {
            logger.error("Error performing NEFT transaction from account number: {} to account number: {} - {}", fromAccountNumber, toAccountNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    /*
     * Retrieves the most recent transaction for a given account number.
     
     GET--> http://localhost:8085/api/account/recent-transaction/1234889519
     */
    @GetMapping("/recent-transaction/{accountNumber}")
    public ResponseEntity<Transaction> getRecentTransaction(@PathVariable String accountNumber) {
        logger.info("Request received to get the most recent transaction for account number: {}", accountNumber);

        try {
            Transaction transaction = transactionService.getRecentTransaction(accountNumber);
            logger.info("Retrieved recent transaction for account number: {}", accountNumber);
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving recent transaction for account number: {} - {}", accountNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }   
}
