package com.example.saudejapostosaudeservice.infrastructure.input.api.security;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.infrastructure.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@Profile("api")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadArgumentException.class })
    public ProblemDetail handleBadRequest(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = { UnauthorizedException.class })
    public ProblemDetail handleUnauthorized(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value = { NaoEncontradoException.class })
    public ProblemDetail handleNotFound(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = { RuntimeException.class })
    public ProblemDetail handleRuntime(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Recurso não encontrado: %s", ((ServletWebRequest) request).getRequest().getRequestURI())));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format("Requisição mal realizada: %s", ex.getMessage())));
    }
}
