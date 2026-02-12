package com.example.saudejapostosaudeservice.mappers;

import com.example.saudejapostosaudeservice.dtos.SolicitacaoVinculoPacientePostoSaudeDto;
import com.example.saudejapostosaudeservice.entidades.SolicitacaoVinculoPacientePostoSaude;
import dtos.responses.SolicitacaoVinculoPacientePostoSaudeResponse;

public class SolicitacaoVinculoPacientePostoSaudeMapper {

    public static SolicitacaoVinculoPacientePostoSaude toEntidade(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        return new SolicitacaoVinculoPacientePostoSaude(solicitacaoVinculoPacientePostoSaudeDto.id(), solicitacaoVinculoPacientePostoSaudeDto.paciente(),
                solicitacaoVinculoPacientePostoSaudeDto.postoSaude(), solicitacaoVinculoPacientePostoSaudeDto.consumida());
    }

    public static SolicitacaoVinculoPacientePostoSaudeDto toDto(SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaude) {
        return new  SolicitacaoVinculoPacientePostoSaudeDto(solicitacaoVinculoPacientePostoSaude.getId(), solicitacaoVinculoPacientePostoSaude.getPaciente(),
                solicitacaoVinculoPacientePostoSaude.getPostoSaude(), solicitacaoVinculoPacientePostoSaude.isConsumida());
    }

    public static SolicitacaoVinculoPacientePostoSaudeResponse toResponse(SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaude) {
        return new SolicitacaoVinculoPacientePostoSaudeResponse(solicitacaoVinculoPacientePostoSaude.getId(),
                solicitacaoVinculoPacientePostoSaude.getPaciente(), solicitacaoVinculoPacientePostoSaude.getPostoSaude(),
                solicitacaoVinculoPacientePostoSaude.isConsumida());
    }
}
