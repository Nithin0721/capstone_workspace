package com.wipro.training.bank.model;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 1:44:11 pm
Project: smart-bank
 */

@Data
public class Address {
	
	private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String state;
    private String city;
    private String pincode;

}
