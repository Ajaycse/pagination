package com.xapp.jpa.pagination.service;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> type, Object id) {
        super(String.format("Unable to find %s id=%s", type.getSimpleName(), id));
    }
}
