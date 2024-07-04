package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class DesignationException extends ApiException {
    public DesignationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
