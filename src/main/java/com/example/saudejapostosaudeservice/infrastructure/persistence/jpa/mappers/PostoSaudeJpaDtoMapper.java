package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers;

import com.example.saudejapostosaudeservice.dtos.PostoSaudeDto;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models.PostoSaudeJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Profile("jpa")
public class PostoSaudeJpaDtoMapper {

    @Autowired
    private EnderecoPostoSaudeJpaDtoMapper enderecoPostoSaudeJpaDtoMapper;

    public PostoSaudeJpa toJpa(PostoSaudeDto postoSaudeDto) {
        return new PostoSaudeJpa(postoSaudeDto.id(), postoSaudeDto.nome(),
                Objects.isNull(postoSaudeDto.enderecoPostoSaudeDto()) ? null : enderecoPostoSaudeJpaDtoMapper.toJpa(postoSaudeDto.enderecoPostoSaudeDto()));
    }

    public PostoSaudeDto toDto(PostoSaudeJpa postoSaudeJpa) {
        return new PostoSaudeDto(postoSaudeJpa.getId(), postoSaudeJpa.getNome(),
                Objects.isNull(postoSaudeJpa.getEnderecoPostoSaude()) ? null : enderecoPostoSaudeJpaDtoMapper.toDto(postoSaudeJpa.getEnderecoPostoSaude()));
    }
}
