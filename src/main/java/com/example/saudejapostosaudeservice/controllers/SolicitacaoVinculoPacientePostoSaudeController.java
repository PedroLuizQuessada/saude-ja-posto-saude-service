package com.example.saudejapostosaudeservice.controllers;

import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.UsuarioDataSource;
import com.example.saudejapostosaudeservice.entidades.SolicitacaoVinculoPacientePostoSaude;
import com.example.saudejapostosaudeservice.gateways.NotificacaoGateway;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.SolicitacaoVinculoPacientePostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.UsuarioGateway;
import com.example.saudejapostosaudeservice.mappers.SolicitacaoVinculoPacientePostoSaudeMapper;
import com.example.saudejapostosaudeservice.usecases.ConsumirSolicitacaoVinculoPacientePostoSaudeUseCase;
import com.example.saudejapostosaudeservice.usecases.SolicitarVinculoPacientePostoSaudeUseCase;
import dtos.responses.SolicitacaoVinculoPacientePostoSaudeResponse;

public class SolicitacaoVinculoPacientePostoSaudeController {

    private final PostoSaudeDataSource postoSaudeDataSource;
    private final SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource;
    private final UsuarioDataSource usuarioDataSource;
    private final NotificacaoDataSource notificacaoDataSource;

    public SolicitacaoVinculoPacientePostoSaudeController(PostoSaudeDataSource postoSaudeDataSource, SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.postoSaudeDataSource = postoSaudeDataSource;
        this.solicitacaoVinculoPacientePostoSaudeDataSource = solicitacaoVinculoPacientePostoSaudeDataSource;
        this.usuarioDataSource = usuarioDataSource;
        this.notificacaoDataSource = notificacaoDataSource;
    }

    public SolicitacaoVinculoPacientePostoSaudeResponse solicitarVinculoPacientePostoSaude(Long paciente, Long postoSaude) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway = new SolicitacaoVinculoPacientePostoSaudeGateway(solicitacaoVinculoPacientePostoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);

        SolicitarVinculoPacientePostoSaudeUseCase useCase = new SolicitarVinculoPacientePostoSaudeUseCase(postoSaudeGateway,
                solicitacaoVinculoPacientePostoSaudeGateway, usuarioGateway, notificacaoGateway);

        SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaude = useCase.executar(paciente, postoSaude);
        return SolicitacaoVinculoPacientePostoSaudeMapper.toResponse(solicitacaoVinculoPacientePostoSaude);
    }

    public void consumirSolicitacaoVinculoPacientePostoSaude(Long profissionalSaudeId, Long solicitacaoVinculoPacientePostoSaudeId) {
        SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway = new SolicitacaoVinculoPacientePostoSaudeGateway(solicitacaoVinculoPacientePostoSaudeDataSource);
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        ConsumirSolicitacaoVinculoPacientePostoSaudeUseCase useCase =
                new ConsumirSolicitacaoVinculoPacientePostoSaudeUseCase(solicitacaoVinculoPacientePostoSaudeGateway, postoSaudeGateway, usuarioGateway, notificacaoGateway);

        useCase.executar(profissionalSaudeId, solicitacaoVinculoPacientePostoSaudeId);
    }
}
