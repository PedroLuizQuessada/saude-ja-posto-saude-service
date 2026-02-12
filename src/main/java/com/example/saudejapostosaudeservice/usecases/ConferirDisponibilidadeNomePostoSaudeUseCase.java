package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;

public class ConferirDisponibilidadeNomePostoSaudeUseCase {

    private final PostoSaudeGateway postoSaudeGateway;

    public ConferirDisponibilidadeNomePostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
    }

    public void executar(String nome) {
        if (postoSaudeGateway.countByNome(nome) > 0) {
            throw new BadArgumentException("Nome já está sendo utilizado.");
        }
    }
}
