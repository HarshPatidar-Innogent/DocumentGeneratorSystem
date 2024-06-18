package com.dgs.exception.CustomException;

public class DocumentCreationException extends RuntimeException{
    public DocumentCreationException(String message) {
        super(message);
    }
}
