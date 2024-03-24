package com.mjc.school.controller;

import com.mjc.school.service.errorsexceptions.NotFoundException;
import com.mjc.school.service.errorsexceptions.ValidatorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException nfe) {
        return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<String> handleValidatorException(ValidatorException ve) {
        return new ResponseEntity<>(ve.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        String bodyMsg = "No value is present in DB, please reconsider your request.";
        return new ResponseEntity<>(bodyMsg, HttpStatus.NOT_FOUND);
    }
}