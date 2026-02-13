package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.gateways.NotificacaoGateway;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.UsuarioGateway;
import dtos.requests.EnviarNotificacaoRequest;
import dtos.requests.ProfissionalSaudeIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

import java.util.List;
import java.util.Objects;

public class VincularProfissionalSaudePostoSaudeUseCase {

    private static final String ASSUNTO_NOTIFICACAO = "Vínculo ao posto de saúde do Saúde Já.";
    private static final String MENSAGEM_NOTIFICACAO = "Seu usuário foi vínculado como profissional da saúde ao posto de saúde %s.";

    private final PostoSaudeGateway postoSaudeGateway;
    private final UsuarioGateway usuarioGateway;
    private final NotificacaoGateway notificacaoGateway;

    public VincularProfissionalSaudePostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway, UsuarioGateway usuarioGateway, NotificacaoGateway notificacaoGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.usuarioGateway = usuarioGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    public void executar(Long profissionalSaudeExecutandoId, Long profissionalSaudeVincularId, Long postoSaudeId) {
        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);
        if (postoSaudeGateway.isPostoSaudeContainsAnyProfissionalSaude(postoSaudeId) &&
                !postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeExecutandoId, postoSaudeId))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        if (postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeVincularId, postoSaudeId))
            throw new BadArgumentException(String.format("Profissional de saude %d já está vinculado ao posto de saúde %d", profissionalSaudeVincularId, postoSaudeId));

        UsuarioEmailPageResponse usuarioEmailPageResponse = usuarioGateway.getUsuarioProfissionalSaudeEmailFromId(new ProfissionalSaudeIdPageRequest(0, 1, List.of(profissionalSaudeVincularId)));
        if (Objects.isNull(usuarioEmailPageResponse.getContent()) || usuarioEmailPageResponse.getContent().isEmpty()
                || Objects.isNull(usuarioEmailPageResponse.getContent().getFirst()) || usuarioEmailPageResponse.getContent().getFirst().isBlank())
            throw new NaoEncontradoException(String.format("E-mail do profissional da saúde %d não encontrado.", profissionalSaudeVincularId));

        postoSaudeGateway.vincularProfissionalSaudePostoSaude(profissionalSaudeVincularId, postoSaudeId);
//        notificacaoGateway.enviarNotificacao(new EnviarNotificacaoRequest(usuarioEmailPageResponse.getContent().getFirst(),
//                ASSUNTO_NOTIFICACAO, String.format(MENSAGEM_NOTIFICACAO, postoSaude.getNome())));
    }
}
