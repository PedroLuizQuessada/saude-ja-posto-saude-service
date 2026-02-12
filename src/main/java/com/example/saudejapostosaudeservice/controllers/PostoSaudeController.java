package com.example.saudejapostosaudeservice.controllers;

import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.UsuarioDataSource;
import com.example.saudejapostosaudeservice.entidades.PacienteIdPage;
import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.entidades.PostoSaudePage;
import com.example.saudejapostosaudeservice.entidades.ProfissionalSaudeIdPage;
import com.example.saudejapostosaudeservice.gateways.NotificacaoGateway;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.SolicitacaoVinculoPacientePostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.UsuarioGateway;
import com.example.saudejapostosaudeservice.mappers.PostoSaudeMapper;
import com.example.saudejapostosaudeservice.usecases.*;
import dtos.requests.AtualizarPostoSaudeRequest;
import dtos.requests.CriarPostoSaudeRequest;
import dtos.responses.PacienteIdPageResponse;
import dtos.responses.PostoSaudePageResponse;
import dtos.responses.PostoSaudeResponse;
import dtos.responses.ProfissionalSaudeIdPageResponse;
import enums.EstadoEnum;

import java.util.List;

public class PostoSaudeController {

    private final PostoSaudeDataSource postoSaudeDataSource;
    private final SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource;
    private final UsuarioDataSource usuarioDataSource;
    private final NotificacaoDataSource notificacaoDataSource;

    public PostoSaudeController(PostoSaudeDataSource postoSaudeDataSource, SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.postoSaudeDataSource = postoSaudeDataSource;
        this.solicitacaoVinculoPacientePostoSaudeDataSource = solicitacaoVinculoPacientePostoSaudeDataSource;
        this.usuarioDataSource = usuarioDataSource;
        this.notificacaoDataSource = notificacaoDataSource;
    }

    public PostoSaudeResponse criarPostoSaude(CriarPostoSaudeRequest criarPostoSaudeRequest) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        CriarPostoSaudeUseCase useCase = new CriarPostoSaudeUseCase(postoSaudeGateway);

        PostoSaude postoSaude = useCase.executar(criarPostoSaudeRequest);

        return PostoSaudeMapper.toResponse(postoSaude);
    }

    public PostoSaudeResponse atualizarPostoSaude(Long profissionalSaudeId, Long postoSaudeId, AtualizarPostoSaudeRequest atualizarPostoSaudeRequest) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        AtualizarPostoSaudeUseCase useCase = new AtualizarPostoSaudeUseCase(postoSaudeGateway);

        PostoSaude postoSaude = useCase.executar(profissionalSaudeId, postoSaudeId, atualizarPostoSaudeRequest);

        return PostoSaudeMapper.toResponse(postoSaude);
    }

    public PostoSaudePageResponse getPostoSaude(int page, int size, boolean ord, String cidade, EstadoEnum estado) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        GetPostoSaudeUseCase useCase = new GetPostoSaudeUseCase(postoSaudeGateway);

        PostoSaudePage postoSaudePage = useCase.executar(page, size, ord, cidade, estado);

        List<PostoSaudeResponse> postoSaudeResponseList = postoSaudePage.getContent().stream().map(PostoSaudeMapper::toResponse).toList();
        return new PostoSaudePageResponse(postoSaudePage.getPage(), postoSaudePage.getSize(), postoSaudeResponseList);
    }

    public PacienteIdPageResponse getPacienteList(int page, int size, boolean ord, Long profissionalSaudeId, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        GetPacienteListUseCase useCase = new GetPacienteListUseCase(postoSaudeGateway);

        PacienteIdPage pacienteIdPage = useCase.executar(page, size, ord, profissionalSaudeId, postoSaudeId);
        return new PacienteIdPageResponse(pacienteIdPage.getPage(), pacienteIdPage.getSize(), pacienteIdPage.getContent());
    }

    public ProfissionalSaudeIdPageResponse getProfissionalSaudeList(int page, int size, boolean ord, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        GetProfissionalSaudeListUseCase useCase = new GetProfissionalSaudeListUseCase(postoSaudeGateway);

        ProfissionalSaudeIdPage profissionalSaudeIdPage = useCase.executar(page, size, ord, postoSaudeId);
        return new ProfissionalSaudeIdPageResponse(profissionalSaudeIdPage.getPage(), profissionalSaudeIdPage.getSize(), profissionalSaudeIdPage.getContent());
    }

    public void removerPaciente(Long profissionalSaudeId, Long pacienteId, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway = new SolicitacaoVinculoPacientePostoSaudeGateway(solicitacaoVinculoPacientePostoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        RemoverPacientePostoSaudeUseCase useCase = new RemoverPacientePostoSaudeUseCase(postoSaudeGateway, solicitacaoVinculoPacientePostoSaudeGateway, usuarioGateway, notificacaoGateway);

        useCase.executar(profissionalSaudeId, pacienteId, postoSaudeId);
    }

    public void removerPaciente(Long pacienteId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway = new SolicitacaoVinculoPacientePostoSaudeGateway(solicitacaoVinculoPacientePostoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        RemoverPacientePostoSaudeUseCase useCase = new RemoverPacientePostoSaudeUseCase(postoSaudeGateway, solicitacaoVinculoPacientePostoSaudeGateway, usuarioGateway, notificacaoGateway);

        useCase.executar(pacienteId);
    }

    public void vincularProfissionalSaudePostoSaude(Long profissionalSaudeExecutandoId, Long profissionalSaudeVincularId, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        VincularProfissionalSaudePostoSaudeUseCase useCase = new VincularProfissionalSaudePostoSaudeUseCase(postoSaudeGateway, usuarioGateway, notificacaoGateway);
        useCase.executar(profissionalSaudeExecutandoId, profissionalSaudeVincularId, postoSaudeId);
    }

    public void removerProfissionalSaudePostoSaude(Long profissionalSaudeExecutandoId, Long profissionalSaudeRemoverId, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        RemoverProfissionalSaudePostoSaudeUseCase useCase = new RemoverProfissionalSaudePostoSaudeUseCase(postoSaudeGateway, usuarioGateway, notificacaoGateway);
        useCase.executar(profissionalSaudeExecutandoId, profissionalSaudeRemoverId, postoSaudeId);
    }

    public void removerProfissionalSaudePostoSaude(Long profissionalSaudeRemoverId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        RemoverProfissionalSaudePostoSaudeUseCase useCase = new RemoverProfissionalSaudePostoSaudeUseCase(postoSaudeGateway, usuarioGateway, notificacaoGateway);
        useCase.executar(profissionalSaudeRemoverId);
    }

    public void apagarPostoSaude(Long profissionalSaudeId, Long postoSaudeId) {
        PostoSaudeGateway postoSaudeGateway = new PostoSaudeGateway(postoSaudeDataSource);
        SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway =
                new SolicitacaoVinculoPacientePostoSaudeGateway(solicitacaoVinculoPacientePostoSaudeDataSource);
        ApagarPostoSaudeUseCase useCase = new ApagarPostoSaudeUseCase(postoSaudeGateway, solicitacaoVinculoPacientePostoSaudeGateway);
        useCase.executar(profissionalSaudeId, postoSaudeId);
    }
}
