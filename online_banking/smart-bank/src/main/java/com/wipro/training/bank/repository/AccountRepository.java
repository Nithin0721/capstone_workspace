package com.wipro.training.bank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wipro.training.bank.model.Account;

/**
Author : Nithin T
Date   : 13-Sept-2024
Time   : 2:45:11 pm
Project: smart-bank
 */

public interface AccountRepository extends MongoRepository<Account, String>{

	Account findByAccountNumber(String accountNumber);
    Account findByUserId(String userId);
    
    @Query("{'personalDetails.adhar': ?0}")
    Account findByAdhar(String adhar);
    
    // Similarly, update the other methods
    @Query("{'personalDetails.mobile': ?0}")
    Account findByMobile(String mobile);
    
    @Query("{'personalDetails.email': ?0}")
    Account findByEmail(String email);
}
