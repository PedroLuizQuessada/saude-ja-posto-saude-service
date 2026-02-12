package com.example.saudejapostosaudeservice.entidades;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class EnderecoPostoSaude {
    private final Long id;
    private EstadoEnum estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    @Setter
    private String complemento;
    private String cep;

    public EnderecoPostoSaude(Long id, EstadoEnum estado, String cidade, String bairro, String rua, String numero, String complemento, String cep) {
        String mensagemErro = "";

        try {
            validarEstado(estado);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarCidade(cidade);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarBairro(bairro);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarRua(rua);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarNumero(numero);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarCep(cep);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    public void setEstado(EstadoEnum estado) {
        validarEstado(estado);
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        validarCidade(cidade);
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        validarBairro(bairro);
        this.bairro = bairro;
    }

    public void setRua(String rua) {
        validarRua(rua);
        this.rua = rua;
    }

    public void setNumero(String numero) {
        validarNumero(numero);
        this.numero = numero;
    }

    public void setCep(String cep) {
        validarCep(cep);
        this.cep = cep;
    }

    private void validarEstado(EstadoEnum estado) {
        if (Objects.isNull(estado))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir um estado.");
    }

    private void validarCidade(String cidade) {
        if (Objects.isNull(cidade))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir uma cidade.");
    }

    private void validarBairro(String bairro) {
        if (Objects.isNull(bairro))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir um bairro.");
    }

    private void validarRua(String rua) {
        if (Objects.isNull(rua))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir uma rua.");
    }

    private void validarNumero(String numero) {
        if (Objects.isNull(numero))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir um número.");
    }

    private void validarCep(String cep) {
        if (Objects.isNull(cep))
            throw new BadArgumentException("O endereço do posto de saúde deve possuir um CEP.");
    }
}
