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

public class ConsumirSolicitacaoVinculoPacientePostoSaudeUseCase {

    private static final String ASSUNTO_NOTIFICACAO = "Vínculo ao posto de saúde do Saúde Já.";
    private static final String MENSAGEM_NOTIFICACAO = "Seu usuário foi vínculado como paciente ao posto de saúde %s.";

    private final SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway;
    private final PostoSaudeGateway postoSaudeGateway;
    private final UsuarioGateway usuarioGateway;
    private final NotificacaoGateway notificacaoGateway;

    public ConsumirSolicitacaoVinculoPacientePostoSaudeUseCase(SolicitacaoVinculoPacientePostoSaudeGateway solicitacaoVinculoPacientePostoSaudeGateway, PostoSaudeGateway postoSaudeGateway, UsuarioGateway usuarioGateway, NotificacaoGateway notificacaoGateway) {
        this.solicitacaoVinculoPacientePostoSaudeGateway = solicitacaoVinculoPacientePostoSaudeGateway;
        this.postoSaudeGateway = postoSaudeGateway;
        this.usuarioGateway = usuarioGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    public void executar(Long profissionalSaudeId, Long solicitacaoVinculoPacientePostoSaudeId) {
        SolicitacaoVinculoPacientePostoSaude solicitacaoVinculoPacientePostoSaude =
                solicitacaoVinculoPacientePostoSaudeGateway.getSolicitacaoVinculoPacientePostoSaudeById(solicitacaoVinculoPacientePostoSaudeId);

        if (!postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeId, solicitacaoVinculoPacientePostoSaude.getPostoSaude()))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        if (solicitacaoVinculoPacientePostoSaude.isConsumida())
            throw new BadArgumentException("Solicitação de vínculo de paciente em posto de saúde.");

        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(solicitacaoVinculoPacientePostoSaude.getPostoSaude());

        solicitacaoVinculoPacientePostoSaude.setConsumida(true);

        UsuarioEmailPageResponse usuarioEmailPageResponse = usuarioGateway.getUsuarioPacienteEmailFromId(new PacienteIdPageRequest(0, 1, List.of(solicitacaoVinculoPacientePostoSaude.getPaciente())));
        if (Objects.isNull(usuarioEmailPageResponse.getContent()) || usuarioEmailPageResponse.getContent().isEmpty()
                || Objects.isNull(usuarioEmailPageResponse.getContent().getFirst()) || usuarioEmailPageResponse.getContent().getFirst().isBlank())
            throw new NaoEncontradoException(String.format("E-mail do paciente %d não encontrado.", solicitacaoVinculoPacientePostoSaude.getPaciente()));

        solicitacaoVinculoPacientePostoSaudeGateway.consumirSolicitacaoVinculoPacientePostoSaude(
                SolicitacaoVinculoPacientePostoSaudeMapper.toDto(solicitacaoVinculoPacientePostoSaude));
        postoSaudeGateway.vincularPaciente(solicitacaoVinculoPacientePostoSaude.getPaciente(), solicitacaoVinculoPacientePostoSaude.getPostoSaude());
        notificacaoGateway.enviarNotificacao(new EnviarNotificacaoRequest(usuarioEmailPageResponse.getContent().getFirst(),
                ASSUNTO_NOTIFICACAO, String.format(MENSAGEM_NOTIFICACAO, postoSaude.getNome())));
    }
}
