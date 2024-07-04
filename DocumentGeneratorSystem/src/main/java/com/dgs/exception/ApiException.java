package com.dgs.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ApiException extends RuntimeException{
    final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, Exception exception, HttpStatus httpStatus) {
        super(message, exception);
        this.httpStatus = httpStatus;
    }
}
