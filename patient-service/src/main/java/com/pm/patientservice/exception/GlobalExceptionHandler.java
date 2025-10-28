package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// ControllerAdvice class allows centralized exception handling and makes the controller code a lot cleaner
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // all the JPA level exceptions like the invalidity of some input etc is caught and placed within ex and this class
    // allows us to shape the error message to have more detail and show what we want to the user
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();

        // we are getting all the errors from the ex variable
        // from that we are getting all the field errors
        // and,we are adding that info to the errors hashmap
        // key is the field associated with the error and value is any related error message
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(),error.getDefaultMessage()));

        // badRequest means that a 400 status code is passed to the calling client along with a json having all errors
        return ResponseEntity.badRequest().body(errors);
    }

    // there are two steps in the below error handler
    // one is for the client and one is for us to catch any errors
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        log.warn("Email already exists {}", ex.getMessage()); // printing out the error and also getting the error
        // message in case it contains any valuable information
        Map<String,String> errors = new HashMap<>();
        errors.put("message","Email already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("Patient not found {}", ex.getMessage());
        Map<String,String> errors = new HashMap<>();
        errors.put("message","Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }
}
