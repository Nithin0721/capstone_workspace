package com.wipro.training.bank.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 1:48:01 pm
Project: smart-bank
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
	
    private double totalAmount;
    private List<Transaction> transactions = new ArrayList<>();

}
