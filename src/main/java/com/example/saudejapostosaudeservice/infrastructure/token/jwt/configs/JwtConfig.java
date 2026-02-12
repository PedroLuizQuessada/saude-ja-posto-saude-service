package com.example.saudejapostosaudeservice.infrastructure.token.jwt.configs;

import com.example.saudejapostosaudeservice.infrastructure.token.jwt.validadores.ValidadorAudiencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.security.interfaces.RSAPublicKey;

@Configuration
@Profile("jwt")
public class JwtConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.audiencia}")
    private String audiencia;

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(key).build();
        OAuth2TokenValidator<Jwt> withAudience = new ValidadorAudiencia(audiencia);

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withAudience));
        return decoder;
    }

    @Bean
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");
        return converter;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }
}
