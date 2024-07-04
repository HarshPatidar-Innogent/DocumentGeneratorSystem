package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class DocumentCreationException extends ApiException {
    public DocumentCreationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
