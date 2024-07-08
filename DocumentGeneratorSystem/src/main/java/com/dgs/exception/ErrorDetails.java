package com.dgs.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ErrorDetails {
    private LocalTime timestamp;
    private String message;
    private String details;

//    private ErrorDetails() {
//        timestamp = LocalTime.now();
//    }
//
//    public ErrorDetails(String message) {
//        this.message = message;
//    }
//
//    public ErrorDetails(LocalTime timestamp, String message) {
//        this.timestamp = timestamp;
//        this.message = message;
//    }

    public ErrorDetails(LocalTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


}
