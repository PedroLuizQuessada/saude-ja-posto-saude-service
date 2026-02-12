package com.example.saudejapostosaudeservice.infrastructure.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Recurso não permitido.");
    }
}
