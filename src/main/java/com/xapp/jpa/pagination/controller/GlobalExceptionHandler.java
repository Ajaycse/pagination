package com.xapp.jpa.pagination.controller;

import com.xapp.jpa.pagination.service.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e)
    {
        System.out.println("Global Exception handler {} "+ e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
