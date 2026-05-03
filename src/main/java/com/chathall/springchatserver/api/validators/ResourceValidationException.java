package com.chathall.springchatserver.api.validators;

public class ResourceValidationException extends RuntimeException {

    public ResourceValidationException(String resourceType, String message) {
        super("Validation failed for resource " + resourceType + ": " + message);
    }
}
