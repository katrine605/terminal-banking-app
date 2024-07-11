package com.revature.exception;

public class AccountUpdateException extends RuntimeException{
    public AccountUpdateException(String message){
        super(message);
    }
}
