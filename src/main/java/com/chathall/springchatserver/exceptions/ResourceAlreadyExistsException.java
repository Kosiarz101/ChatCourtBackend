package com.chathall.springchatserver.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends CustomAppException {

    public ResourceAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, "Resource Already Exists", message);
    }
}
