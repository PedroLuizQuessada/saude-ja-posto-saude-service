package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.SolicitacaoVinculoPacientePostoSaudeGateway;

public class ApagarPostoSaudeUseCase {

    private final PostoSaudeGateway postoSaudeGateway;
    private final SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway;

    public ApagarPostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway, SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.solicitacaoVinculoPacientePostoSaudeGateway = solicitacaoVinculoPacientePostoSaudeGateway;
    }

    public void executar(Long profissionalSaudeId, Long postoSaudeId) {
        postoSaudeGateway.getPostoSaudeById(postoSaudeId);

        if (!postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeId, postoSaudeId))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        postoSaudeGateway.deletePostoSaudeById(postoSaudeId);
        solicitacaoVinculoPacientePostoSaudeGateway.deleteByPostoSaudeId(postoSaudeId);
    }
}
