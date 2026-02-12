package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.entidades.ProfissionalSaudeIdPage;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;

public class GetProfissionalSaudeListUseCase {

    private final PostoSaudeGateway postoSaudeGateway;

    public GetProfissionalSaudeListUseCase(PostoSaudeGateway postoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
    }

    public ProfissionalSaudeIdPage executar(int page, int size, boolean ord, Long postoSaudeId) {
        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);
        return postoSaudeGateway.getProfissionalSaudeList(page, size, ord, postoSaude.getId());
    }
}
