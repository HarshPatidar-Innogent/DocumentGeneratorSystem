package com.dgs.exception;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ErrorDetails {
    private LocalTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
