package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers;

import com.example.saudejapostosaudeservice.dtos.EnderecoPostoSaudeDto;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models.EnderecoPostoSaudeJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class EnderecoPostoSaudeJpaDtoMapper {
    public EnderecoPostoSaudeJpa toJpa(EnderecoPostoSaudeDto enderecoPostoSaudeDto) {
        return new EnderecoPostoSaudeJpa(enderecoPostoSaudeDto.id(), enderecoPostoSaudeDto.estado(), enderecoPostoSaudeDto.cidade(),
                enderecoPostoSaudeDto.bairro(), enderecoPostoSaudeDto.rua(), enderecoPostoSaudeDto.numero(), enderecoPostoSaudeDto.complemento(),
                enderecoPostoSaudeDto.cep());
    }

    public EnderecoPostoSaudeDto toDto(EnderecoPostoSaudeJpa enderecoPostoSaudeJpa) {
        return new EnderecoPostoSaudeDto(enderecoPostoSaudeJpa.getId(), enderecoPostoSaudeJpa.getEstado(), enderecoPostoSaudeJpa.getCidade(),
                enderecoPostoSaudeJpa.getBairro(), enderecoPostoSaudeJpa.getRua(), enderecoPostoSaudeJpa.getNumero(),
                enderecoPostoSaudeJpa.getComplemento(), enderecoPostoSaudeJpa.getCep());
    }
}
