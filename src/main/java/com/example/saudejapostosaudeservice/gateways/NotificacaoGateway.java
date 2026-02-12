package com.example.saudejapostosaudeservice.gateways;

import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import dtos.requests.EnviarNotificacaoRequest;

public class NotificacaoGateway {
    private final NotificacaoDataSource notificacaoDataSource;

    public NotificacaoGateway(NotificacaoDataSource notificacaoDataSource) {
        this.notificacaoDataSource = notificacaoDataSource;
    }

    public void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest) {
        notificacaoDataSource.enviarNotificacao(enviarNotificacaoRequest);
    }
}
