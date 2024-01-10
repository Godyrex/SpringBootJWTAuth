package com.example.springbootjwtauth.exceptions;

public class RoleAlreadyAssignedException extends RuntimeException{
    public RoleAlreadyAssignedException(String message) {
        super(message);
    }
}
