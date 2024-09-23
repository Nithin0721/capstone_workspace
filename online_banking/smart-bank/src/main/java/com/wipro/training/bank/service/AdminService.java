package com.wipro.training.bank.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.wipro.training.bank.dao.AdminRequest;
import com.wipro.training.bank.exception.AccountNotFoundException;
import com.wipro.training.bank.exception.InsufficientBalanceException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Admin;
import com.wipro.training.bank.model.Transaction;
import com.wipro.training.bank.repository.AccountRepository;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:26:51 pm
Project: smart-bank
 */
@Service
public class AdminService {

	private final AccountRepository accountRepository;

	public AdminService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Admin creditMoney(AdminRequest request) {
		Account account = accountRepository.findByAccountNumber(request.getAccountNumber());
		if (account == null) {
			throw new AccountNotFoundException("Account not found with account number: " + request.getAccountNumber());
		}

		Admin admin = account.getAdmin();
		if (admin == null) {
			// Initialize admin if not already present
			admin = new Admin();
			account.setAdmin(admin);
		}

		double newTotalAmount = admin.getTotalAmount() + request.getAmount();
		admin.setTotalAmount(newTotalAmount);

		// Create and add transaction
		Transaction transaction = new Transaction(
				request.getAmount(),
				"CREDIT",
				newTotalAmount,
				LocalDateTime.now()
				);
		admin.getTransactions().add(transaction);

		accountRepository.save(account);
		return admin;
	}

	public Admin debitMoney(AdminRequest request) {
	    // Find the account by account number
	    Account account = accountRepository.findByAccountNumber(request.getAccountNumber());
	    if (account == null) {
	        throw new AccountNotFoundException("Account not found with account number: " + request.getAccountNumber());
	    }

	    // Get the admin details from the account
	    Admin admin = account.getAdmin();
	    if (admin == null) {
	        // Initialize admin if not already present
	        admin = new Admin();
	        account.setAdmin(admin);
	    }

	    // Check if the account has enough balance for the debit operation
	    double currentTotalAmount = admin.getTotalAmount();
	    if (currentTotalAmount < request.getAmount()) {
	    	
	        throw new InsufficientBalanceException("Insufficient balance. Available balance: " 
	        + currentTotalAmount);
	    }

	    // Deduct the amount and update the total balance
	    double newTotalAmount = currentTotalAmount - request.getAmount();
	    admin.setTotalAmount(newTotalAmount);

	    // Create and add a debit transaction
	    Transaction transaction = new Transaction(
	            request.getAmount(),
	            "DEBIT",
	            newTotalAmount,
	            LocalDateTime.now()
	    );
	    admin.getTransactions().add(transaction);

	    // Save the updated account
	    accountRepository.save(account);
	    
	    return admin;
	}
	
	
}