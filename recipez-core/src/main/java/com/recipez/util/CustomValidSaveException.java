package com.recipez.util;

public class CustomValidSaveException extends RuntimeException {
    public CustomValidSaveException (String message){
        super(message);
    }

    public CustomValidSaveException (String message, Throwable cause) {
        super(message, cause);
    }
}
