package com.sammidev.exception;

public class StudentNotFoundException extends IllegalStateException{
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}
