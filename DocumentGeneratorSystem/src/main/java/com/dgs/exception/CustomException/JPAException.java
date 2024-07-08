package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class JPAException extends ApiException {
    public JPAException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
