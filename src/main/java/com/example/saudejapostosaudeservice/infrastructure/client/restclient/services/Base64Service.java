package com.example.saudejapostosaudeservice.infrastructure.client.restclient.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Profile("restclient")
public class Base64Service {
    String getUsuarioSenhaCriptografado(String usuario, String senha) {
        String combinado = usuario + ":" + senha;
        return Base64.getEncoder().encodeToString(combinado.getBytes(StandardCharsets.UTF_8));
    }
}
