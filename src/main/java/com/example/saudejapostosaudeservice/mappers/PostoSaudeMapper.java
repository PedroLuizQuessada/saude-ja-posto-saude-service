package com.example.saudejapostosaudeservice.mappers;

import com.example.saudejapostosaudeservice.dtos.PostoSaudeDto;
import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import dtos.requests.CriarPostoSaudeRequest;
import dtos.responses.PostoSaudeResponse;

import java.util.Objects;

public class PostoSaudeMapper {

    public static PostoSaude toEntidade(CriarPostoSaudeRequest criarPostoSaudeRequest) {
        return new PostoSaude(null, criarPostoSaudeRequest.nome(),
                Objects.isNull(criarPostoSaudeRequest.enderecoPostoSaudeRequest()) ? null :
                        EnderecoPostoSaudeMapper.toEntidade(criarPostoSaudeRequest.enderecoPostoSaudeRequest()));
    }

    public static PostoSaude toEntidade(PostoSaudeDto postoSaudeDto) {
        return new PostoSaude(postoSaudeDto.id(), postoSaudeDto.nome(),
                Objects.isNull(postoSaudeDto.enderecoPostoSaudeDto()) ? null :
                        EnderecoPostoSaudeMapper.toEntidade(postoSaudeDto.enderecoPostoSaudeDto()));
    }

    public static PostoSaudeDto toDto(PostoSaude postoSaude) {
        return new PostoSaudeDto(postoSaude.getId(), postoSaude.getNome(),
                Objects.isNull(postoSaude.getEnderecoPostoSaude()) ? null :
                        EnderecoPostoSaudeMapper.toDto(postoSaude.getEnderecoPostoSaude()));
    }

    public static PostoSaudeResponse toResponse(PostoSaude postoSaude) {
        return new PostoSaudeResponse(postoSaude.getId(), postoSaude.getNome(),
                Objects.isNull(postoSaude.getEnderecoPostoSaude()) ? null :
                        EnderecoPostoSaudeMapper.toResponse(postoSaude.getEnderecoPostoSaude()));
    }
}
