package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PacienteIdPage;
import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;

public class GetPacienteListUseCase {

    private final PostoSaudeGateway postoSaudeGateway;

    public GetPacienteListUseCase(PostoSaudeGateway postoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
    }

    public PacienteIdPage executar(int page, int size, boolean ord, Long profissionalSaudeId, Long postoSaudeId) {
        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);

        if (!postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeId, postoSaudeId))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        return postoSaudeGateway.getPacienteList(page, size, ord, postoSaude.getId());
    }
}
