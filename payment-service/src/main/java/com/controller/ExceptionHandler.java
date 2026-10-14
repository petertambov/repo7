package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.dto.ErrorResponseDto;
import com.exception.MyException;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponseDto> handle(MyException exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getStatusStr(),
                exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handle(Exception exception) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}    

