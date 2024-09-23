package com.wipro.training.bank.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.wipro.training.bank.dao.LoginRequest;
import com.wipro.training.bank.exception.ResourceNotFoundException;
import com.wipro.training.bank.model.Account;
import com.wipro.training.bank.repository.AccountRepository;
import com.wipro.training.bank.util.EncryptionUtil;
/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 9:06:28 pm
Project: smart-bank
 */
@Service
public class AuthenticationService {

	private final AccountRepository accountRepository;

	//DI using constructor
	public AuthenticationService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account login(LoginRequest loginRequest) {
		Account account = accountRepository.findByUserId(loginRequest.getUserId());
		if (account != null) {
			// Decrypt the stored password and compare with the provided password
			String decryptedPassword = EncryptionUtil.decrypt(account.getLoginPassword());
			if (decryptedPassword.equals(loginRequest.getLoginPassword())) {
				return account; // Login successful
			} else {
				throw new IllegalArgumentException("Invalid login credentials");
			}
		} else {
			throw new IllegalArgumentException("Account not found");
		}
	}

	public String generateOtp(String userId) {
		// Generate a 6-digit OTP
		String otp = String.format("%06d", random.nextInt(1000000));
		otpStorage.put(userId, otp); // Store OTP temporarily
		return otp;
	}

	public boolean verifyOtp(String userId, String otp) {
		String storedOtp = otpStorage.get(userId);
		return storedOtp != null && storedOtp.equals(otp);
	}

	private final Map<String, String> otpStorage = new HashMap<>(); // Temporary in-memory OTP storage
	private final Random random = new Random();

	//Setting new password	
	public Account setNewPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword) {
		Account account = accountRepository.findByUserId(userId);
		if (account == null) {
			throw new ResourceNotFoundException("User not found");
		}

		// Decrypt the current password and check if it matches the provided old password
		String decryptedOldPassword = EncryptionUtil.decrypt(account.getLoginPassword());
		if (!decryptedOldPassword.equals(oldPassword)) {
			throw new IllegalArgumentException("Old password is incorrect");
		}

		// Check if new password and confirmation match
		if (!newPassword.equals(confirmNewPassword)) {
			throw new IllegalArgumentException("New password and confirmation password do not match");
		}

		// Encrypt the new password and save it
		account.setLoginPassword(EncryptionUtil.encrypt(newPassword));
		return accountRepository.save(account);
	}


}
