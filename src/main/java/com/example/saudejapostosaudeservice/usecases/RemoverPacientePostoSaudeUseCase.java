package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.gateways.NotificacaoGateway;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.UsuarioGateway;
import dtos.requests.EnviarNotificacaoRequest;
import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

import java.util.List;
import java.util.Objects;

public class RemoverPacientePostoSaudeUseCase {

    private static final String ASSUNTO_NOTIFICACAO = "Remoção do posto de saúde do Saúde Já.";
    private static final String MENSAGEM_NOTIFICACAO = "Seu usuário foi removido como paciente do posto de saúde %s.";

    private final PostoSaudeGateway postoSaudeGateway;
    private final UsuarioGateway usuarioGateway;
    private final NotificacaoGateway notificacaoGateway;

    public RemoverPacientePostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway, UsuarioGateway usuarioGateway, NotificacaoGateway notificacaoGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.usuarioGateway = usuarioGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    public void executar(Long profissionalSaudeId, Long pacienteId, Long postoSaudeId) {
        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);

        if (!postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeId, postoSaudeId))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        if (!postoSaudeGateway.isPostoSaudeContainsPaciente(pacienteId, postoSaudeId))
            throw new BadArgumentException(String.format("O paciente %d não faz parte do posto de saúde %d",  pacienteId, postoSaudeId));

        UsuarioEmailPageResponse usuarioEmailPageResponse = usuarioGateway.getUsuarioPacienteEmailFromId(new PacienteIdPageRequest(0, 1, List.of(pacienteId)));
        if (Objects.isNull(usuarioEmailPageResponse.getContent()) || usuarioEmailPageResponse.getContent().isEmpty()
                || Objects.isNull(usuarioEmailPageResponse.getContent().getFirst()) || usuarioEmailPageResponse.getContent().getFirst().isBlank())
            throw new NaoEncontradoException(String.format("E-mail do paciente %d não encontrado.", pacienteId));

        postoSaudeGateway.removerPaciente(pacienteId, postoSaudeId);
        notificacaoGateway.enviarNotificacao(new EnviarNotificacaoRequest(usuarioEmailPageResponse.getContent().getFirst(),
                ASSUNTO_NOTIFICACAO, String.format(MENSAGEM_NOTIFICACAO, postoSaude.getNome())));
    }
}
