package com.example.saudejapostosaudeservice.infrastructure.client.restclient.services;

import com.example.saudejapostosaudeservice.controllers.AutenticacaoController;
import com.example.saudejapostosaudeservice.datasources.AutenticacaoDataSource;
import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import com.example.saudejapostosaudeservice.infrastructure.exceptions.ForbiddenException;
import com.example.saudejapostosaudeservice.infrastructure.exceptions.UnauthorizedException;
import dtos.requests.EnviarNotificacaoRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("restclient")
public class NotificacaoRestClientImpl implements NotificacaoDataSource {

    @Value("${notificacao-service.base-url}")
    private String urlBase;

    @Value("${notificacao-service.audiencia}")
    private String audiencia;

    private final RestClient client;

    private final AutenticacaoController autenticacaoController;

    private final ClientResponseService clientResponseService;

    public NotificacaoRestClientImpl(RestClient client, AutenticacaoDataSource autenticacaoDataSource, ClientResponseService clientResponseService) {
        this.client = client;
        this.autenticacaoController = new AutenticacaoController(autenticacaoDataSource);
        this.clientResponseService = clientResponseService;
    }

    @Override
    public void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest) {
        client.post()
                .uri(urlBase + "/api/v1/notificacoes/enviar")
                .header("Authorization", "Bearer " + autenticacaoController.gerarTokenServico(audiencia))
                .body(enviarNotificacaoRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    String body = clientResponseService.getResponseBody(res);

                    if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                        throw new BadRequestException(
                                "Valores inválidos para a notificação a ser enviada. Corpo: " + body
                        );
                    }
                    if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        throw new UnauthorizedException();
                    }
                    if (res.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                        throw new ForbiddenException();
                    }
                    else {
                        throw new RuntimeException(
                                "Falha no serviço de notificações (notificacao-service). Corpo: " + body
                        );
                    }
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    String body = clientResponseService.getResponseBody(res);

                    throw new RuntimeException(
                            "Erro interno no serviço de notificações (notificacao-service). Corpo: " + body
                    );
                })
                .toEntity(Void.class);
    }

}
