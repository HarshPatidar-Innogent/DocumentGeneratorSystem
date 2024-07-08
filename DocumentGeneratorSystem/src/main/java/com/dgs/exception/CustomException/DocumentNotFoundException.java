package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends ApiException {
    public DocumentNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
