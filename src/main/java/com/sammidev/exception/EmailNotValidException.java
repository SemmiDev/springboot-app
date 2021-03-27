package com.sammidev.exception;

public class EmailNotValidException extends IllegalStateException{
    public EmailNotValidException(String msg) {
        super(msg);
    }
}
