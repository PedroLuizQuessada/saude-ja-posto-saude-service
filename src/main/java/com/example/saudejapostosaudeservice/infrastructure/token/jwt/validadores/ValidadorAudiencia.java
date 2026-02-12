package com.example.saudejapostosaudeservice.infrastructure.token.jwt.validadores;

import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@Profile("jwt")
public class ValidadorAudiencia implements OAuth2TokenValidator<Jwt> {
    private final String audiencia;

    public ValidadorAudiencia(String audiencia) {
        this.audiencia = audiencia;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        List<String> audienciasToken = token.getAudience();
        if (audienciasToken != null && audienciasToken.stream().anyMatch(audiencia::contains)) {
            return OAuth2TokenValidatorResult.success();
        }
        OAuth2Error error = new OAuth2Error("invalid_token",
                "O token não contém a audiência esperada.", null);
        return OAuth2TokenValidatorResult.failure(error);
    }
}
