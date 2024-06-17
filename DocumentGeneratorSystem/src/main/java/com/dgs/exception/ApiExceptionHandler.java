package com.dgs.exception;

import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.exception.CustomException.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {TemplateNotFoundException.class})
    public ResponseEntity<Object> handleTemplateNotFoundException(TemplateNotFoundException templateNotFoundException) {
        ApiException apiException = new ApiException(
                templateNotFoundException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public static ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ApiException apiException = new ApiException(
                userNotFoundException.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);

    }
}
