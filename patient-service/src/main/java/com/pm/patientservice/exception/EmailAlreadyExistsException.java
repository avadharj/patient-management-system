package com.pm.patientservice.exception;

// we are extending the Runtime Exception
// if we directly used RuntimeException, it would be hard to debug exactly what the problem was
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
