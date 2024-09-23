package com.wipro.training.bank.exception;
/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 3:24:21 pm
Project: smart-bank
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
