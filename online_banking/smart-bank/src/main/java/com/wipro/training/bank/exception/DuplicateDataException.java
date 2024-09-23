package com.wipro.training.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
Author : Nithin T
Date   : 17-Sept-2024
Time   : 9:40:30 am
Project: smart-bank
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateDataException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateDataException(String message) {
        super(message);
    }
}