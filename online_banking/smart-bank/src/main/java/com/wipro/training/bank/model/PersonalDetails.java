package com.wipro.training.bank.model;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 1:53:19 pm
Project: smart-bank
 */

@Data
public class PersonalDetails {
	
	private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fatherName;
    
    @Indexed(unique = true)
    private String mobile;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String adhar;

    private String dob;

}
