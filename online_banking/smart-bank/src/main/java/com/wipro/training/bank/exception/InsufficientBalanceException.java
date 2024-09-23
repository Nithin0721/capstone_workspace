package com.wipro.training.bank.exception;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 5:26:21 pm
Project: smart-bank
 */

public class InsufficientBalanceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String message) {
        super(message);
    }
}

