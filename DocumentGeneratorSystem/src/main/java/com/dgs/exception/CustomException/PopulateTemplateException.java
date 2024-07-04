package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PopulateTemplateException extends ApiException {
    public PopulateTemplateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
