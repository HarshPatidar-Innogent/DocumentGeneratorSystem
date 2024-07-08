package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TemplateException extends ApiException {
    public TemplateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
