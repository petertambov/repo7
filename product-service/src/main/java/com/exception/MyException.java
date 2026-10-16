package com.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException{
    private final HttpStatus status;
    private final String statusStr;

    public MyException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.statusStr = String.valueOf(status);
    }
    public HttpStatus getStatus() {
        return status;
    }

    public String getStatusStr() {
        return statusStr;
    }
}

