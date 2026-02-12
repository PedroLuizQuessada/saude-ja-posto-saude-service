package com.example.saudejapostosaudeservice.gateways;

import com.example.saudejapostosaudeservice.datasources.UsuarioDataSource;
import dtos.requests.PacienteIdPageRequest;
import dtos.requests.ProfissionalSaudeIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

public class UsuarioGateway {
    private final UsuarioDataSource usuarioDataSource;

    public UsuarioGateway(UsuarioDataSource usuarioDataSource) {
        this.usuarioDataSource = usuarioDataSource;
    }

    public UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest) {
        return usuarioDataSource.getUsuarioPacienteEmailFromId(pacienteIdPageRequest);
    }

    public UsuarioEmailPageResponse getUsuarioProfissionalSaudeEmailFromId(ProfissionalSaudeIdPageRequest profissionalSaudeIdPageRequest) {
        return usuarioDataSource.getUsuarioProfissionalSaudeEmailFromId(profissionalSaudeIdPageRequest);
    }
}
