package com.example.saudejapostosaudeservice.infrastructure.input.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile("api")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String mensagem = "Usuário ou senha incorretos";
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/problem+json");
        response.getWriter().write(mapper.writeValueAsString(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, mensagem)));
    }
}
