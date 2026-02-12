package com.example.saudejapostosaudeservice.infrastructure.input.api.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API de postos de saúde", version = "v1.0.0",
                description = "API de postos de saúde para o projeto Saúde Já proposto como tech-challenge da pós-tech de " +
                        "Arquitetura e desenvolvimento em Java da FIAP")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Profile("api")
public class SwaggerConfig {
}
