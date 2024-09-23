package com.wipro.training.bank.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 1:58:41 pm
Project: smart-bank
 */
@NoArgsConstructor
@Data
public class Transaction {
	
	private String fromAccount;
    private String toAccount;
    private double amount;
    private String type; // "CREDIT" or "DEBIT" or "NEFT"
    private double totalAmount; // balance after the transaction
    private LocalDateTime transactionDate;
    private String remark;

    // Constructor for NEFT and other transfers
    public Transaction(String fromAccount, String toAccount, double amount, String type, 
                        double totalAmount, LocalDateTime transactionDate, String remark) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
        this.remark = remark;
    }

 // Constructor for Admin operations (credit/debit)
    public Transaction(double amount, String type, double totalAmount, LocalDateTime transactionDate) {
        this.fromAccount = "NA";
        this.toAccount = "NA";
        this.amount = amount;
        this.type = type;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
        this.remark = "ADMIN";
    }

}