package com.example.saudejapostosaudeservice.exceptions;

public class BadArgumentException extends RuntimeException {
    public BadArgumentException(String message) {
        super(message);
    }
}
