package com.example.saudejapostosaudeservice.usecases;

import com.example.saudejapostosaudeservice.entidades.EnderecoPostoSaude;
import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import com.example.saudejapostosaudeservice.gateways.PostoSaudeGateway;
import com.example.saudejapostosaudeservice.mappers.PostoSaudeMapper;
import dtos.requests.AtualizarPostoSaudeRequest;

import java.util.Objects;

public class AtualizarPostoSaudeUseCase {

    private final PostoSaudeGateway postoSaudeGateway;
    private final ConferirDisponibilidadeNomePostoSaudeUseCase conferirDisponibilidadeNomePostoSaudeUseCase;

    public AtualizarPostoSaudeUseCase(PostoSaudeGateway postoSaudeGateway) {
        this.postoSaudeGateway = postoSaudeGateway;
        this.conferirDisponibilidadeNomePostoSaudeUseCase = new ConferirDisponibilidadeNomePostoSaudeUseCase(postoSaudeGateway);
    }

    public PostoSaude executar(Long profissionalSaudeId, Long postoSaudeId, AtualizarPostoSaudeRequest atualizarPostoSaudeRequest) {
        PostoSaude postoSaude = postoSaudeGateway.getPostoSaudeById(postoSaudeId);
        EnderecoPostoSaude enderecoPostoSaude = postoSaude.getEnderecoPostoSaude();

        if (!postoSaudeGateway.isPostoSaudeContainsProfissionalSaude(profissionalSaudeId, postoSaudeId))
            throw new BadArgumentException("O profissional da saúde não faz parte do posto de saúde informado.");

        if (Objects.isNull(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest()))
            throw new BadArgumentException("O posto de saúde deve possuir um endereço.");

        String mensagemErro = "";
        try {
            enderecoPostoSaude.setEstado(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().estado());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setCidade(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().cidade());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setBairro(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().bairro());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setRua(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().rua());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setNumero(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().numero());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setComplemento(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().complemento());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            enderecoPostoSaude.setCep(atualizarPostoSaudeRequest.enderecoPostoSaudeRequest().cep());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            postoSaude.setNome(atualizarPostoSaudeRequest.nome());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            postoSaude.setEnderecoPostoSaude(enderecoPostoSaude);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        conferirDisponibilidadeNomePostoSaudeUseCase.executar(atualizarPostoSaudeRequest.nome());

        return postoSaudeGateway.savePostoSaude(PostoSaudeMapper.toDto(postoSaude));
    }
}
