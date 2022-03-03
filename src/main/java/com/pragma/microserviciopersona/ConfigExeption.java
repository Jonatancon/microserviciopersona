package com.pragma.microserviciopersona;

import com.pragma.microserviciopersona.domain.exceptions.ConflictException;
import com.pragma.microserviciopersona.domain.exceptions.InternalExceptio;
import com.pragma.microserviciopersona.domain.exceptions.NotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConfigExeption {

    @ExceptionHandler(NotFountException.class)
    public ResponseEntity<String> notFound(NotFountException notFountException) {
        return new ResponseEntity<>(notFountException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflict (ConflictException conflict) {
        return new ResponseEntity<>(conflict.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalExceptio.class)
    public ResponseEntity<String> internal(InternalExceptio internal) {
        return new ResponseEntity<>(internal.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
