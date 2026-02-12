package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models;

import com.example.saudejapostosaudeservice.infrastructure.exceptions.BadJpaArgumentException;
import enums.EstadoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Entity
@Table(name = "enderecos_posto_saude")
@Getter
@NoArgsConstructor
@Profile("jpa")
public class EnderecoPostoSaudeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnum estado;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    @Column
    private String complemento;

    @Column(nullable = false)
    private String cep;

    public EnderecoPostoSaudeJpa(Long id, EstadoEnum estado, String cidade, String bairro, String rua, String numero, String complemento, String cep) {
        String message = "";

        try {
            validarEstado(estado);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarCidade(cidade);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarBairro(bairro);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarRua(rua);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarNumero(numero);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarCep(cep);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    private void validarEstado(EstadoEnum estado) {
        if (Objects.isNull(estado))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir um estado para ser armazenado no banco de dados.");
    }

    private void validarCidade(String cidade) {
        if (Objects.isNull(cidade))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir uma cidade para ser armazenado no banco de dados.");

        if (cidade.length() > 255)
            throw new BadJpaArgumentException("A cidade do endereço do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }

    private void validarBairro(String bairro) {
        if (Objects.isNull(bairro))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir um bairro para ser armazenado no banco de dados.");

        if (bairro.length() > 255)
            throw new BadJpaArgumentException("O bairro do endereço do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }

    private void validarRua(String rua) {
        if (Objects.isNull(rua))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir uma rua para ser armazenado no banco de dados.");

        if (rua.length() > 255)
            throw new BadJpaArgumentException("A rua do endereço do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }

    private void validarNumero(String numero) {
        if (Objects.isNull(numero))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir um número para ser armazenado no banco de dados.");

        if (numero.length() > 255)
            throw new BadJpaArgumentException("O número do endereço do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }

    private void validarCep(String cep) {
        if (Objects.isNull(cep))
            throw new BadJpaArgumentException("O endereço do posto de saúde deve possuir um CEP para ser armazenado no banco de dados.");

        if (cep.length() > 255)
            throw new BadJpaArgumentException("O CEP do endereço do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }
}
