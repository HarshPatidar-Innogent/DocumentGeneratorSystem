package com.dgs.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final Throwable throwable;

    public ApiException(String message,
                        HttpStatus httpStatus, ZonedDateTime timestamp, Throwable throwable) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.throwable = throwable;
        this.timestamp = timestamp;
    }
}
