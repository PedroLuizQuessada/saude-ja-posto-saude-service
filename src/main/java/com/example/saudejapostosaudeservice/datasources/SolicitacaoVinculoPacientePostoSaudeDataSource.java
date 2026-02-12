package com.example.saudejapostosaudeservice.datasources;

import com.example.saudejapostosaudeservice.dtos.SolicitacaoVinculoPacientePostoSaudeDto;

import java.util.Optional;

public interface SolicitacaoVinculoPacientePostoSaudeDataSource {
    Long countByPacienteAndPostoSaudeWhereNotConsumida(Long paciente, Long postoSaude);
    SolicitacaoVinculoPacientePostoSaudeDto criarSolicitacaoVinculoPacientePostoSaude(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto);
    Optional<SolicitacaoVinculoPacientePostoSaudeDto> getSolicitacaoVinculoPacientePostoSaudeDtoById(Long id);
    void consumirSolicitacaoContaUsuario(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto);
    void deleteByPostoSaudeId(Long postoSaudeId);
    void deleteByPacienteId(Long pacienteId);
}
