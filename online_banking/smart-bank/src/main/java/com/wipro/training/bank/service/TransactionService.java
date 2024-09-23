package com.wipro.training.bank.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Transaction;
import com.wipro.training.bank.repository.AccountRepository;

/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:07:41 pm
Project: smart-bank
 */

@Service
public class TransactionService {

	private final AccountRepository accountRepository;

	//DI using constructor
	public TransactionService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Transaction> getTransactionDetails(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account != null && account.getAdmin() != null) {
			return account.getAdmin().getTransactions();
		} else {
			throw new IllegalArgumentException("Account or transaction details not found");
		}
	}

	public Account performNEFTTransaction(String fromAccountNumber, String toAccountNumber, double amount, String remark) {
		// Find both accounts
		Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
		Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

		if (fromAccount == null || toAccount == null) {
			throw new ResourceNotFoundException("One or both accounts not found");
		}

		// Check if sender has sufficient balance
		double currentBalance = fromAccount.getAdmin().getTotalAmount();
		if (currentBalance < amount) {
			throw new IllegalArgumentException("Insufficient funds in the account");
		}

		// Debit transaction for sender's account
		Transaction debitTransaction = new Transaction();
		debitTransaction.setFromAccount(fromAccountNumber);
		debitTransaction.setToAccount(toAccountNumber);
		debitTransaction.setAmount(amount);
		debitTransaction.setType("NEFT DEBIT");
		debitTransaction.setTotalAmount(currentBalance - amount);
		debitTransaction.setTransactionDate(LocalDateTime.now());
		debitTransaction.setRemark(remark);

		// Update sender's balance and save transaction
		fromAccount.getAdmin().setTotalAmount(currentBalance - amount);
		fromAccount.getAdmin().getTransactions().add(debitTransaction);
		accountRepository.save(fromAccount);

		// Credit transaction for recipient's account
		Transaction creditTransaction = new Transaction();
		creditTransaction.setFromAccount(fromAccountNumber);
		creditTransaction.setToAccount(toAccountNumber);
		creditTransaction.setAmount(amount);
		creditTransaction.setType("NEFT CREDIT");
		creditTransaction.setTotalAmount(toAccount.getAdmin().getTotalAmount() + amount);
		creditTransaction.setTransactionDate(LocalDateTime.now());
		creditTransaction.setRemark(remark);

		// Update recipient's balance and save transaction
		toAccount.getAdmin().setTotalAmount(toAccount.getAdmin().getTotalAmount() + amount);
		toAccount.getAdmin().getTransactions().add(creditTransaction);
		accountRepository.save(toAccount);

		return fromAccount;  // Returning the updated sender's account

	}
	public Transaction getRecentTransaction(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			List<Transaction> transactions = account.getAdmin().getTransactions();
			if (transactions != null && !transactions.isEmpty()) {
				// Sort transactions by date in descending order and return the most recent one
				transactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));
				return transactions.get(0); // Return the most recent transaction
			} else {
				throw new ResourceNotFoundException("No transactions found for account number " + accountNumber);
			}
		} else {
			throw new ResourceNotFoundException("Account with account number " + accountNumber + " not found.");
		}
	}
}


