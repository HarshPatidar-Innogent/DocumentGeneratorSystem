package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TemplateNotFoundException extends ApiException {
    public TemplateNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
