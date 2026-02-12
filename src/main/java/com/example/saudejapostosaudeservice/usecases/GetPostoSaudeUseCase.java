package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaudePage;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import enums.EstadoEnum;

public class GetPostoSaudeUseCase {

    private final PostoSaudeGateway postosaudeGateway;

    public GetPostoSaudeUseCase(PostoSaudeGateway postosaudeGateway) {
        this.postosaudeGateway = postosaudeGateway;
    }

    public PostoSaudePage executar(int page, int size, boolean ord, String cidade, EstadoEnum estado) {
        return postosaudeGateway.getPostoSaude(page, size, ord, cidade, estado);
    }
}
