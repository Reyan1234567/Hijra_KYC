package com.example.hijra_kyc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errors=new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach((error)->{errors.put(error.getDefaultMessage(),error.getDefaultMessage());});
        return ResponseEntity.badRequest().body(errors);
    }
}


