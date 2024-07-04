package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class DocumentPopulationException extends ApiException {
    public DocumentPopulationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
