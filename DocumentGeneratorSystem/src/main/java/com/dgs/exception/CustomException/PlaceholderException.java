package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PlaceholderException extends ApiException {
    public PlaceholderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
