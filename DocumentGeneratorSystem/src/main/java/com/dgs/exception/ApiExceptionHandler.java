package com.dgs.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> handleException(ApiException exception, WebRequest request, HttpServletRequest reponse) {
        log.info(exception.getMessage());
        HttpStatus httpStatus = exception.getHttpStatus();
        Integer errorCode = httpStatus.value();
        String errorMessage = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage, httpStatus.name());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception, WebRequest request, HttpServletRequest response) throws ApiException {
        log.error(exception.getMessage());
        return handleException(new ApiException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), request, response);
    }
}
