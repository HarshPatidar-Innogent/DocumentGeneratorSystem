package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import com.dgs.exception.ApiExceptionHandler;
import org.springframework.http.HttpStatus;

public class AccessControlException extends ApiException {
    public AccessControlException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
