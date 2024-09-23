package com.wipro.training.bank.dao;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:33:11 pm
Project: smart-bank
 */

@Data
public class AccountDetailsDTO {

	private String accountNumber;
	private String userId;
	
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fatherName;

	private String email;
	private String mobile;
	private String adhar;
	private String dob;
	
	private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String state;
    private String city;
    private String pincode;
}
