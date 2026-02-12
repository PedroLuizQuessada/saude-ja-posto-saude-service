package com.example.saudejapostosaudeservice.datasources;

public interface AutenticacaoDataSource {
    String gerarTokenServico(String audiencia);
}
