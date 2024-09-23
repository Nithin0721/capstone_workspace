package com.wipro.training.bank.dao;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 3:25:16 pm
Project: smart-bank
 */

@Data
public class RegisterRequest {

	private String accountNumber;
	private String userId;
	private String loginPassword;
	private String confirmLoginPassword;
	private String transactionPassword;
	private String confirmTransactionPassword;

}
