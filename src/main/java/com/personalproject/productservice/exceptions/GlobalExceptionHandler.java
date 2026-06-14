package com.personalproject.productservice.exceptions;

import com.personalproject.productservice.dtos.ErrorDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ErrorDTO NullPointerException() {
        ErrorDTO error = new ErrorDTO();
        error.setStatus("Failure");
        error.setMessage("Null Pointer Exception Occured");
        return error;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> ProductNotFoundException(ProductNotFoundException productNotFoundException) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus("Failure");
        error.setMessage(productNotFoundException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> CategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus("Failure");
        error.setMessage(categoryNotFoundException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
