package com.example.objects.exception;

public class InvalidInputDataException extends RuntimeException {
    public InvalidInputDataException(String msg) {
        super(msg);
    }
}