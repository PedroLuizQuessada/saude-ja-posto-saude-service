package com.example.saudejapostosaudeservice.infrastructure.exceptions;

public class BadJpaArgumentException extends RuntimeException {
    public BadJpaArgumentException(String message) {
        super(message);
    }
}
