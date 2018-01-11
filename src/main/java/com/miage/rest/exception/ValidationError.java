package com.miage.rest.exception;

import java.util.ArrayList;
import java.util.List;
 
public class ValidationError {
 
    private List<FieldError> fieldErrors = new ArrayList<>();
 
    public ValidationError() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }
}  