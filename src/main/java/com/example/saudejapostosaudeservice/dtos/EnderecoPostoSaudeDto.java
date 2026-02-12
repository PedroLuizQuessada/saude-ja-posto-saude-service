package com.example.saudejapostosaudeservice.dtos;

import enums.EstadoEnum;

public record EnderecoPostoSaudeDto(Long id, EstadoEnum estado, String cidade, String bairro, String rua, String numero, String complemento, String cep) {
}
