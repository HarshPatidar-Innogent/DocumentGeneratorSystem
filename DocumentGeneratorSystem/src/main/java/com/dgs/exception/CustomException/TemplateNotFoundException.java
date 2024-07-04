package com.dgs.exception.CustomException;


public class TemplateNotFoundException extends RuntimeException{
    public TemplateNotFoundException(Long id) {
        super("Template with ID " + id + " not found");

    }
}
