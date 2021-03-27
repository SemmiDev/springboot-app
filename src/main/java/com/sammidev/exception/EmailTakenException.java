package com.sammidev.exception;

public class EmailTakenException extends IllegalStateException{
    public EmailTakenException(String msg) {
        super(msg);
    }
}