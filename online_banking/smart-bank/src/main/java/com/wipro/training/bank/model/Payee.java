package com.wipro.training.bank.model;

import lombok.Data;

/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 3:19:13 pm
Project: smart-bank
 */
@Data
public class Payee {
	
	private String beneficiaryName;
    private String accountNumber;
    private String nickname; // Optional field

}
