package com.example.saudejapostosaudeservice.entidades;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PostoSaude {
    private final Long id;
    private String nome;
    private EnderecoPostoSaude enderecoPostoSaude;

    public PostoSaude(Long id, String nome, EnderecoPostoSaude enderecoPostoSaude) {
        String mensagemErro = "";

        try {
            validarNome(nome);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarEnderecoPostoSaude(enderecoPostoSaude);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        this.id = id;
        this.nome = nome;
        this.enderecoPostoSaude = enderecoPostoSaude;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void setEnderecoPostoSaude(EnderecoPostoSaude enderecoPostoSaude) {
        validarEnderecoPostoSaude(enderecoPostoSaude);
        this.enderecoPostoSaude = enderecoPostoSaude;
    }

    private void validarNome(String nome) {
        if (Objects.isNull(nome) || nome.isEmpty())
            throw new BadArgumentException("O posto de saúde deve possuir um nome.");
    }

    private void validarEnderecoPostoSaude(EnderecoPostoSaude enderecoPostoSaude) {
        if (Objects.isNull(enderecoPostoSaude)) {
            throw new BadArgumentException("O posto de saúde deve possuir um endereço.");
        }
    }
}
