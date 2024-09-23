package com.wipro.training.bank.exception;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 4:43:11 pm
Project: smart-bank
 */

public class AccountNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountNotFoundException(String message) {
        super(message);
    }

}
