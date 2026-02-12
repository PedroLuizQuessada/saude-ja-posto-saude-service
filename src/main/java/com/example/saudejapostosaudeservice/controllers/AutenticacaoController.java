package com.example.saudejapostosaudeservice.controllers;

import com.example.saudejapostosaudeservice.datasources.AutenticacaoDataSource;
import com.example.saudejapostosaudeservice.gateways.AutenticacaoGateway;
import com.example.saudejapostosaudeservice.usecases.GerarTokenServicoUseCase;

public class AutenticacaoController {

    private final AutenticacaoDataSource autenticacaoDataSource;

    public AutenticacaoController(AutenticacaoDataSource autenticacaoDataSource) {
        this.autenticacaoDataSource = autenticacaoDataSource;
    }

    public String gerarTokenServico(String audiencia) {
        AutenticacaoGateway autenticacaoGateway = new AutenticacaoGateway(autenticacaoDataSource);
        GerarTokenServicoUseCase useCase = new GerarTokenServicoUseCase(autenticacaoGateway);

        return useCase.executar(audiencia);
    }
}
