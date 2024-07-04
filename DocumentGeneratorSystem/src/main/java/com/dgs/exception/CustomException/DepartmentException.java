package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class DepartmentException extends ApiException {
    public DepartmentException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
