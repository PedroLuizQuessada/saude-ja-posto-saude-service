package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.entidades.SolicitacaoVinculoPacientePostoSaude;
import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.gateways.NotificacaoGateway;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.SolicitacaoVinculoPacientePostoSaudeGateway;
import com.example.saudejapostosaudeservice.gateways.UsuarioGateway;
import com.example.saudejapostosaudeservice.mappers.SolicitacaoVinculoPacientePostoSaudeMapper;
import dtos.requests.EnviarNotificacaoRequest;
import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

import java.util.List;
import java.util.Objects;

public class SolicitarVinculoPacientePostoSaudeUseCase {

    private static final String ASSUNTO_NOTIFICACAO = "Solicitação de vínculo do paciente ao posto de saúde do Saúde Já.";
    private static final String MENSAGEM_NOTIFICACAO = "Foi aberta uma solicitação de vínculo para o seu usuário ao posto de saúde %s.";

    private final PostoSaudeGateway postoSaudeGateway;
    private final SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway;
    private final UsuarioGateway usuarioGateway;
    private final NotificacaoGateway notificacaoGateway;

    public SolicitarVinculoPacientePostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway, SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway, UsuarioGateway usuarioGateway, NotificacaoGateway notificacaoGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.solicitacaoVinculoPacientePostoSaudeGateway = solicitacaoVinculoPacientePostoSaudeGateway;
        this.usuarioGateway = usuarioGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    public SolicitacaoVinculoPacientePostoSaude executar(Long pacienteId, Long postoSaudeId) {
        SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaude =
                new SolicitacaoVinculoPacientePostoSaude(null, pacienteId, postoSaudeId, false);

        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);

        if (postoSaudeGateway.isPostoSaudeContainsPaciente(pacienteId, postoSaudeId) ||
                solicitacaoVinculoPacientePostoSaudeGateway.countByPacienteAndPostoSaudeWhereNotConsumida(pacienteId, postoSaudeId) != 0)
            throw new BadArgumentException("O paciente já consta no posto de saúde informado.");

        UsuarioEmailPageResponse usuarioEmailPageResponse = usuarioGateway.getUsuarioPacienteEmailFromId(new PacienteIdPageRequest(0, 1, List.of(pacienteId)));
        if (Objects.isNull(usuarioEmailPageResponse.getContent()) || usuarioEmailPageResponse.getContent().isEmpty()
                || Objects.isNull(usuarioEmailPageResponse.getContent().getFirst()) || usuarioEmailPageResponse.getContent().getFirst().isBlank())
            throw new NaoEncontradoException(String.format("E-mail do paciente %d não encontrado.", pacienteId));

        SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaudeCriada =
                solicitacaoVinculoPacientePostoSaudeGateway.criarSolicitacaoVinculoPacientePostoSaude(SolicitacaoVinculoPacientePostoSaudeMapper.toDto(solicitacaoVinculoPacientePostoSaude));
        notificacaoGateway.enviarNotificacao(new EnviarNotificacaoRequest(usuarioEmailPageResponse.getContent().getFirst(),
                ASSUNTO_NOTIFICACAO, String.format(MENSAGEM_NOTIFICACAO, postoSaude.getNome())));
        return solicitacaoVinculoPacientePostoSaudeCriada;
    }
}
