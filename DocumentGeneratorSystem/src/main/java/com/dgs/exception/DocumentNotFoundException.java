package com.dgs.exception;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
