package com.chathall.springchatserver.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceValidationException extends CustomAppException {

    public ResourceValidationException(String resourceType, String message) {
        super(HttpStatus.BAD_REQUEST, "Validation Failed",
                "Validation failed for resource " + resourceType + ": " + message);
    }
}
