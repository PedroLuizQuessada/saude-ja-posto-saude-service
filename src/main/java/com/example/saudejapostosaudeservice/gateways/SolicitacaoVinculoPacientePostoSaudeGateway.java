package com.example.saudejapostosaudeservice.gateways;

import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.dtos.SolicitacaoVinculoPacientePostoSaudeDto;
import com.example.saudejapostosaudeservice.entidades.SolicitacaoVinculoPacientePostoSaude;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.mappers.SolicitacaoVinculoPacientePostoSaudeMapper;

import java.util.Optional;

public class SolicitacaoVinculoPacientePostoSaudeGateway {

    private final SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource;

    public SolicitacaoVinculoPacientePostoSaudeGateway(SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource) {
        this.solicitacaoVinculoPacientePostoSaudeDataSource = solicitacaoVinculoPacientePostoSaudeDataSource;
    }

    public Long countByPacienteAndPostoSaudeWhereNotConsumida(Long paciente, Long postoSaude) {
        return solicitacaoVinculoPacientePostoSaudeDataSource.countByPacienteAndPostoSaudeWhereNotConsumida(paciente, postoSaude);
    }

    public SolicitacaoVinculoPacientePostoSaude criarSolicitacaoVinculoPacientePostoSaude(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDtoCriada =
                solicitacaoVinculoPacientePostoSaudeDataSource.criarSolicitacaoVinculoPacientePostoSaude(solicitacaoVinculoPacientePostoSaudeDto);
        return SolicitacaoVinculoPacientePostoSaudeMapper.toEntidade(solicitacaoVinculoPacientePostoSaudeDtoCriada);
    }

    public SolicitacaoVinculoPacientePostoSaude getSolicitacaoVinculoPacientePostoSaudeById(Long id) {
        Optional<SolicitacaoVinculoPacientePostoSaudeDto> optionalSolicitacaoVinculoPacientePostoSaudeDto =
                solicitacaoVinculoPacientePostoSaudeDataSource.getSolicitacaoVinculoPacientePostoSaudeDtoById(id);

        if (optionalSolicitacaoVinculoPacientePostoSaudeDto.isEmpty())
            throw new NaoEncontradoException(String.format("Solicitação de vínculo de paciente em posto de saúde %d não encontrada.", id));
        return SolicitacaoVinculoPacientePostoSaudeMapper.toEntidade(optionalSolicitacaoVinculoPacientePostoSaudeDto.get());
    }

    public void consumirSolicitacaoVinculoPacientePostoSaude(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        solicitacaoVinculoPacientePostoSaudeDataSource.consumirSolicitacaoContaUsuario(solicitacaoVinculoPacientePostoSaudeDto);
    }

    public void deleteByPostoSaudeId(Long postoSaudeId) {
        solicitacaoVinculoPacientePostoSaudeDataSource.deleteByPostoSaudeId(postoSaudeId);
    }
}
