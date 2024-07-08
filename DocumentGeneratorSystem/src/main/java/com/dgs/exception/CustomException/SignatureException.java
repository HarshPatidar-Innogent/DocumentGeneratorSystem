package com.dgs.exception.CustomException;

import com.dgs.exception.ApiException;
import org.springframework.http.HttpStatus;

public class SignatureException extends ApiException {
    public SignatureException(String message, HttpStatus httpStatus){
        super(message,httpStatus);
    }
}
