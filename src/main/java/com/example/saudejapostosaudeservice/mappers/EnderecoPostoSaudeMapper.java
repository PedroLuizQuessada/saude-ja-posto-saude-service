package com.example.saudejapostosaudeservice.mappers;

import com.example.saudejapostosaudeservice.dtos.EnderecoPostoSaudeDto;
import com.example.saudejapostosaudeservice.entidades.EnderecoPostoSaude;
import dtos.requests.EnderecoPostoSaudeRequest;
import dtos.responses.EnderecoPostoSaudeResponse;

public class EnderecoPostoSaudeMapper {

    public static EnderecoPostoSaude toEntidade(EnderecoPostoSaudeRequest enderecoPostoSaudeRequest) {
        return new EnderecoPostoSaude(null, enderecoPostoSaudeRequest.estado(), enderecoPostoSaudeRequest.cidade(), enderecoPostoSaudeRequest.bairro(),
                enderecoPostoSaudeRequest.rua(), enderecoPostoSaudeRequest.numero(), enderecoPostoSaudeRequest.complemento(), enderecoPostoSaudeRequest.cep());
    }

    public static EnderecoPostoSaude toEntidade(EnderecoPostoSaudeDto enderecoSaudeDto) {
        return new EnderecoPostoSaude(enderecoSaudeDto.id(), enderecoSaudeDto.estado(), enderecoSaudeDto.cidade(), enderecoSaudeDto.bairro(),
                enderecoSaudeDto.rua(), enderecoSaudeDto.numero(), enderecoSaudeDto.complemento(), enderecoSaudeDto.cep());
    }

    public static EnderecoPostoSaudeDto toDto(EnderecoPostoSaude enderecoSaude) {
        return new EnderecoPostoSaudeDto(enderecoSaude.getId(), enderecoSaude.getEstado(), enderecoSaude.getCidade(), enderecoSaude.getBairro(),
                enderecoSaude.getRua(), enderecoSaude.getNumero(), enderecoSaude.getComplemento(), enderecoSaude.getCep());
    }

    public static EnderecoPostoSaudeResponse toResponse(EnderecoPostoSaude enderecoSaude) {
        return new EnderecoPostoSaudeResponse(enderecoSaude.getId(), enderecoSaude.getEstado(), enderecoSaude.getCidade(), enderecoSaude.getBairro(),
                enderecoSaude.getRua(), enderecoSaude.getNumero(), enderecoSaude.getComplemento(), enderecoSaude.getCep());
    }
}
