package com.wipro.training.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wipro.training.bank.dao.AccountDetailsDTO;
import com.wipro.training.bank.dao.RegisterRequest;
import com.wipro.training.bank.exception.DuplicateDataException;
import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.model.Transaction;
import com.wipro.training.bank.repository.AccountRepository;
import com.wipro.training.bank.util.EncryptionUtil;
/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:13:11 pm
Project: smart-bank
 */
@Service
public class AccountService {

	private final AccountRepository accountRepository;

	//DI using constructor
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
     * Opens a new account by generating a 10-digit account number and saving the account.
     */
    public Account openAccount(Account account) {
        // Check for unique fields
        Account existingAccountByMobile = accountRepository.findByMobile(account.getPersonalDetails().getMobile());
        Account existingAccountByEmail = accountRepository.findByEmail(account.getPersonalDetails().getEmail());
        Account existingAccountByAdhar = accountRepository.findByAdhar(account.getPersonalDetails().getAdhar());

        if (existingAccountByMobile != null || existingAccountByEmail != null || existingAccountByAdhar != null) {
            throw new DuplicateDataException("Mobile, email, or Aadhaar already exists");
        }

     // Generate a 6-digit unique account ID
        account.setAccountId(generateAccountId());
        // Generate a 10-digit unique account number
        account.setAccountNumber(generateAccountNumber());
        return accountRepository.save(account);
    }

    
    private String generateAccountId() {
    	return String.format("%06d", (int) (Math.random() * 1000000));
	}

	
	public Account registerInternetBanking(RegisterRequest request) {
		Account account = accountRepository.findByAccountNumber(request.getAccountNumber());
		if (account != null) {
			if (request.getLoginPassword().equals(request.getConfirmLoginPassword()) &&
					request.getTransactionPassword().equals(request.getConfirmTransactionPassword())) {

				// Encrypt passwords before saving
				account.setUserId(request.getUserId());
				account.setLoginPassword(EncryptionUtil.encrypt(request.getLoginPassword()));
				account.setTransactionPassword(EncryptionUtil.encrypt(request.getTransactionPassword()));

				return accountRepository.save(account);
			} else {
				throw new IllegalArgumentException("Passwords do not match");
			}
		}
		return null;
	}

	public AccountDetailsDTO getAccountDetails(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			AccountDetailsDTO dto = new AccountDetailsDTO();
			dto.setAccountNumber(account.getAccountNumber());
			dto.setUserId(account.getUserId());
			dto.setTitle(account.getPersonalDetails().getTitle());
			dto.setFirstName(account.getPersonalDetails().getFirstName());
			dto.setMiddleName(account.getPersonalDetails().getMiddleName());
			dto.setLastName(account.getPersonalDetails().getLastName());
			dto.setFatherName(account.getPersonalDetails().getFatherName());
			dto.setEmail(account.getPersonalDetails().getEmail());
			dto.setMobile(account.getPersonalDetails().getMobile());
			dto.setAdhar(account.getPersonalDetails().getAdhar());
			dto.setDob(account.getPersonalDetails().getDob());
			dto.setAddressLine1(account.getAddress().getAddressLine1());
			dto.setAddressLine2(account.getAddress().getAddressLine2());
			dto.setLandmark(account.getAddress().getLandmark());
			dto.setCity(account.getAddress().getCity());
			dto.setState(account.getAddress().getState());
			dto.setPincode(account.getAddress().getPincode());

			return dto;
		} else {
			return null;
		}
	}
	
	/**
     * Generates a 10-digit unique account number.
     */
    private String generateAccountNumber() {
        // Logic to generate unique 10-digit account number
        return String.valueOf((long) (Math.random() * 1_000_000_000L + 1_000_000_000L));
    }

	

	/**
     * Retrieves the account summary for a given account number.
     */
    public Map<String, Object> getAccountSummary(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account != null) {
            // Prepare the account summary details
            Map<String, Object> accountSummary = new HashMap<>();
            accountSummary.put("accountNumber", account.getAccountNumber());
            accountSummary.put("accountHolderName", account.getPersonalDetails().getFirstName() + " " +
	                account.getPersonalDetails().getLastName());
            accountSummary.put("balance", account.getAdmin().getTotalAmount());

            // Fetch recent transactions and format them
            List<String> recentTransactions = new ArrayList<>();
            List<Transaction> transactions = account.getAdmin().getTransactions();
            transactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate())); // Sort by most recent
            for (Transaction transaction : transactions) {
                String transactionDetail = "Transaction: " + transaction.getType().toLowerCase() +
                                           " Amount: " + transaction.getAmount();
                recentTransactions.add(transactionDetail);
            }

            // Limit the number of recent transactions to 5 (if needed)
            if (recentTransactions.size() > 5) {
                recentTransactions = recentTransactions.subList(0, 5);
            }

            accountSummary.put("recentTransactions", recentTransactions);

            return accountSummary;
        } else {
            throw new ResourceNotFoundException("Account with account number " + accountNumber + " not found.");
        }
    }
}
		


