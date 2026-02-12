package com.example.saudejapostosaudeservice.infrastructure.client.restclient.services;

import com.example.saudejapostosaudeservice.datasources.AutenticacaoDataSource;
import com.example.saudejapostosaudeservice.infrastructure.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("restclient")
public class AutenticacaoRestClientImpl implements AutenticacaoDataSource {

    @Value("${spring.application.name}")
    private String nomeAplicacao;

    @Value("${saude-ja-posto-saude-service.senha}")
    private String senha;

    @Value("${authentication-service.base-url}")
    private String urlBase;

    private final RestClient client;

    private final Base64Service base64Service;

    private final ClientResponseService clientResponseService;

    public AutenticacaoRestClientImpl(RestClient client, Base64Service base64Service, ClientResponseService clientResponseService) {
        this.client = client;
        this.base64Service = base64Service;
        this.clientResponseService = clientResponseService;
    }

    @Override
    public String gerarTokenServico(String audiencia) {
        return client.post()
                .uri(urlBase + "/api/v1/tokens/" + audiencia)
                .header("Authorization", "Basic " + base64Service.getUsuarioSenhaCriptografado(nomeAplicacao, senha))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) ->
                        {
                            if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
                                throw new UnauthorizedException();
                            else {
                                String body = clientResponseService.getResponseBody(res);
                                throw new RuntimeException("Falha no serviço de autenticação (authentication-service). Corpo: " + body);
                            }
                        }
                )
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) ->
                        {
                            String body = clientResponseService.getResponseBody(res);
                            throw new RuntimeException("Falha no serviço de autenticação (authentication-service). Corpo: " + body);
                        }
                )
                .toEntity(String.class).getBody();
    }
}
