package com.wipro.training.bank.dao;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 3:12:11 pm
Project: smart-bank
 */

@Data
public class AdminRequest {
	
	private String accountNumber;
    private double amount;
    private String username;
    private String password;

}