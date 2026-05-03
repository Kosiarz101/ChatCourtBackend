package com.chathall.springchatserver.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomAppException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "Not Found", message);
    }
}
