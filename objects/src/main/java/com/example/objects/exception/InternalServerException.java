package com.example.objects.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg) {
        super(msg);
    }
}
