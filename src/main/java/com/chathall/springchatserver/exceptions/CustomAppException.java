package com.chathall.springchatserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomAppException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String title = "Internal Server Error";

    public CustomAppException(String message) {
        super(message);
    }

    public CustomAppException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }

    public CustomAppException(HttpStatus status, String title, String message) {
        super(message);
        this.httpStatus = status;
        this.title = title;
    }
}
