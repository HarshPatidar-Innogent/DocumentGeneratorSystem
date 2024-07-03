package com.dgs.exception;

import com.dgs.entity.AccessControl;
import com.dgs.exception.CustomException.*;
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
                ZonedDateTime.now(ZoneId.of("Z")),
                templateNotFoundException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public static ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ApiException apiException = new ApiException(
                userNotFoundException.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z")),
                userNotFoundException
        );
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessControlException.class})
    public static ResponseEntity<Object> handleAccessControl(AccessControlException accessControlException){
        ApiException apiException = new ApiException(
                accessControlException.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z")),
                accessControlException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DocumentCreationException.class})
    public static ResponseEntity<Object> handleDocumentCreationException(DocumentCreationException documentCreationException){
        ApiException apiException = new ApiException(
                documentCreationException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                documentCreationException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DocumentNotFoundException.class})
    public static ResponseEntity<Object> handleDocumentNotFoundException(DocumentNotFoundException documentNotFoundException){
        ApiException apiException = new ApiException(
                documentNotFoundException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                documentNotFoundException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserException.class})
    public static ResponseEntity<Object> handleUserException(UserException userException){
        ApiException apiException = new ApiException(
                userException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                userException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {TemplateException.class})
    public static ResponseEntity<Object> handleTemplateException(TemplateException templateException){
        ApiException apiException = new ApiException(
                templateException.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")),
                templateException
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
