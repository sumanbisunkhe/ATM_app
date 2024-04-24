package com.texas.atm.exception;

public class CustonerNotFoundException extends RuntimeException{
    public CustonerNotFoundException(String message) {
        super(message);
    }
}