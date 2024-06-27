package com.dgs.exception.CustomException;

public class AccessControlException extends RuntimeException{
    public AccessControlException(String message) {
        super(message);
    }
}
