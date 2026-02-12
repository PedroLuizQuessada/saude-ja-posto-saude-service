package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.gateways.AutenticacaoGateway;

public class GerarTokenServicoUseCase {

    private final AutenticacaoGateway autenticacaoGateway;

    public GerarTokenServicoUseCase(AutenticacaoGateway autenticacaoGateway) {
        this.autenticacaoGateway = autenticacaoGateway;
    }

    public String executar(String audiencia) {
        return autenticacaoGateway.gerarTokenServico(audiencia);
    }
}
