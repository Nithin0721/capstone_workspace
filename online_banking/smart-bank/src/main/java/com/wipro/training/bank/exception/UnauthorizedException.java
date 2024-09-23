package com.wipro.training.bank.exception;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:01:31 pm
Project: smart-bank
 */

public class UnauthorizedException extends RuntimeException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
	        super(message);
	    }
}
