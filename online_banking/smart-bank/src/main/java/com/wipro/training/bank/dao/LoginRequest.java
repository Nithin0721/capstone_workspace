package com.wipro.training.bank.dao;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 3:19:21 pm
Project: smart-bank
 */

@Data
public class LoginRequest {

	private String userId;
	private String loginPassword;

}