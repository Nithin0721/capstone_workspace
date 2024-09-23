package com.wipro.training.bank.dao;

import lombok.Data;
/**
Author : Nithin T
Date   : 14-Sept-2024
Time   : 5:11:09 pm
Project: smart-bank
 */
@Data
public class SetNewPasswordRequest {
	
	private String userId;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
