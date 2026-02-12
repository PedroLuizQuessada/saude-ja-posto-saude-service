package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.mappers.PostoSaudeMapper;
import dtos.requests.CriarPostoSaudeRequest;

public class CriarPostoSaudeUseCase {

    private final PostoSaudeGateway postoSaudeGateway;
    private final ConferirDisponibilidadeNomePostoSaudeUseCase conferirDisponibilidadeNomePostoSaudeUseCase;

    public CriarPostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.conferirDisponibilidadeNomePostoSaudeUseCase = new ConferirDisponibilidadeNomePostoSaudeUseCase(postoSaudeGateway);
    }

    public PostoSaude executar(CriarPostoSaudeRequest criarPostoSaudeRequest) {
        PostoSaude postoSaude = PostoSaudeMapper.toEntidade(criarPostoSaudeRequest);
        conferirDisponibilidadeNomePostoSaudeUseCase.executar(criarPostoSaudeRequest.nome());
        return postoSaudeGateway.savePostoSaude(PostoSaudeMapper.toDto(postoSaude));
    }
}
