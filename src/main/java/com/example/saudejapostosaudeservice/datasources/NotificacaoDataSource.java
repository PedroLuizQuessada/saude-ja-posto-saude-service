package com.example.saudejapostosaudeservice.datasources;

import dtos.requests.EnviarNotificacaoRequest;

public interface NotificacaoDataSource {
    void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest);
}
