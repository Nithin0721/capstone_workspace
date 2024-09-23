package com.wipro.training.bank.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 4:49:51 pm
Project: smart-bank
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	    @ExceptionHandler(AccountNotFoundException.class)
	    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
	        logger.error("Account not found: {}", ex.getMessage());
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(UnauthorizedException.class)
	    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
	        logger.error("Unauthorized access: {}", ex.getMessage());
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
	        logger.error("Illegal argument: {}", ex.getMessage());
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(InsufficientBalanceException.class)
	    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGenericException(Exception ex) {
	        logger.error("An error occurred: {}", ex.getMessage());
	        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    @ExceptionHandler(DuplicateDataException.class)
	    public ResponseEntity<Map<String, String>> handleDuplicateDataException(DuplicateDataException ex) {
	        Map<String, String> error = new HashMap<>();
	        error.put("message", ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }
}