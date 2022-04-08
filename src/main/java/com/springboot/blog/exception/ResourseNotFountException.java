package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFountException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourseNotFountException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("The %s not found for %s:%s", resourceName, fieldName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
