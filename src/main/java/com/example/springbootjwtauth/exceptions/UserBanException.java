package com.example.springbootjwtauth.exceptions;

public class UserBanException extends RuntimeException{
    public UserBanException(String message) {
        super(message);
    }
}
