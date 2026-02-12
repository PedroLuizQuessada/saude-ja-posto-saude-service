package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers;

import com.example.saudejapostosaudeservice.dtos.SolicitacaoVinculoPacientePostoSaudeDto;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models.SolicitacaoVinculoPacientePostoSaudeJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class SolicitacaoVinculoPacientePostoSaudeJpaDtoMapper {

    public SolicitacaoVinculoPacientePostoSaudeJpa toJpa(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        return new SolicitacaoVinculoPacientePostoSaudeJpa(solicitacaoVinculoPacientePostoSaudeDto.id(), solicitacaoVinculoPacientePostoSaudeDto.paciente(),
                solicitacaoVinculoPacientePostoSaudeDto.postoSaude(), solicitacaoVinculoPacientePostoSaudeDto.consumida());
    }

    public SolicitacaoVinculoPacientePostoSaudeDto toDto(SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaudeJpa) {
        return new SolicitacaoVinculoPacientePostoSaudeDto(solicitacaoVinculoPacientePostoSaudeJpa.getId(), solicitacaoVinculoPacientePostoSaudeJpa.getPaciente(),
                solicitacaoVinculoPacientePostoSaudeJpa.getPostoSaude(), solicitacaoVinculoPacientePostoSaudeJpa.getConsumida());
    }
}
