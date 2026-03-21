package com.luv4code.springbootlogging.exception;

public class StudentExistsException extends RuntimeException {
    public StudentExistsException(String message) {
        super(message);
    }
}
