package com.revature.exception;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message){
        super(message);
    }
}
