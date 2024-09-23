package com.wipro.training.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Payee;
import com.wipro.training.bank.repository.AccountRepository;
/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:07:05 pm
Project: smart-bank
 */

@Service
public class PayeeService {

	private final AccountRepository accountRepository;

	//DI using constructor
	public PayeeService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	public Account addPayee(String accountNumber, Payee newPayee, String confirmAccountNumber) {
		// Check if the account to which we are adding the payee exists
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new ResourceNotFoundException("Account with account number " + accountNumber + " not found.");
		}

		// Check if the account numbers match
		if (!newPayee.getAccountNumber().equals(confirmAccountNumber)) {
			throw new ResourceNotFoundException("Account number and confirmation account number do not match.");
		}

		// Verify that the payee's account number exists in the database
		Account payeeAccount = accountRepository.findByAccountNumber(newPayee.getAccountNumber());
		if (payeeAccount == null) {
			throw new ResourceNotFoundException("Payee's account number " + newPayee.getAccountNumber() + " not found.");
		}

		// Check if the payee already exists for this account
		boolean payeeExists = account.getPayees().stream()
				.anyMatch(existingPayee -> existingPayee.getAccountNumber().equals(newPayee.getAccountNumber()));

		if (payeeExists) {
			throw new IllegalArgumentException("Payee with account number " + newPayee.getAccountNumber() + " already exists in the account.");
		}

		// Add the new payee to the account's payee list
		account.getPayees().add(newPayee);
		return accountRepository.save(account);
	}

	public List<Payee> getPayees(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			return account.getPayees();
		} else {
			throw new IllegalArgumentException("Account with account number " + accountNumber + " not found.");
		}
	}
}
