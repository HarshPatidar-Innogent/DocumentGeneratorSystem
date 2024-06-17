package com.dgs.exception.CustomException;


public class TemplateNotFoundException extends RuntimeException{
    public TemplateNotFoundException(String message) {
        super(message);
    }
}
