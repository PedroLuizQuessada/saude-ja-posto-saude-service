package com.example.saudejapostosaudeservice.datasources;

import dtos.requests.PacienteIdPageRequest;
import dtos.requests.ProfissionalSaudeIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

public interface UsuarioDataSource {
    UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest);
    UsuarioEmailPageResponse getUsuarioProfissionalSaudeEmailFromId(ProfissionalSaudeIdPageRequest profissionalSaudeIdPageRequest);
}
