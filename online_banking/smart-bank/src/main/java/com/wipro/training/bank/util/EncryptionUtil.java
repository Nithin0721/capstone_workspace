package com.wipro.training.bank.util;

import java.util.Base64;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:39:19 pm
Project: smart-bank
 */

public class EncryptionUtil {


	// Encrypt the password using Base64 encoding
	public static String encrypt(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	// Decrypt the password from Base64 encoding
	public static String decrypt(String encodedPassword) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
		return new String(decodedBytes);
	}

}
