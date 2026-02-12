package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models;

import com.example.saudejapostosaudeservice.infrastructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "postos_saude")
@Getter
@NoArgsConstructor
@Profile("jpa")
public class PostoSaudeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nome;

    @Setter
    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, nullable = false, referencedColumnName = "id", name = "endereco_posto_saude")
    private EnderecoPostoSaudeJpa enderecoPostoSaude;

    @ElementCollection
    @CollectionTable(
            name = "posto_pacientes",
            joinColumns = @JoinColumn(name = "posto_id")
    )
    @Column(name = "paciente_id")
    private List<Long> pacienteList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "posto_profissionais_saude",
            joinColumns = @JoinColumn(name = "posto_id")
    )
    @Column(name = "profissional_saude_id")
    private List<Long> profissionalSaudeList = new ArrayList<>();

    public PostoSaudeJpa(Long id, String nome, EnderecoPostoSaudeJpa enderecoPostoSaude) {
        String message = "";

        try {
            validarNome(nome);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarEnderecoPostoSaude(enderecoPostoSaude);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.nome = nome;
        this.enderecoPostoSaude = enderecoPostoSaude;
    }

    private void validarNome(String nome) {
        if (Objects.isNull(nome))
            throw new BadJpaArgumentException("O posto de saúde deve possuir um nome para ser armazenado no banco de dados.");

        if (nome.length() > 255)
            throw new BadJpaArgumentException("O nome do posto de saúde deve possuir até 255 caracteres para ser armazenado no banco de dados.");
    }

    private void validarEnderecoPostoSaude(EnderecoPostoSaudeJpa enderecoUsuario) {
        if (Objects.isNull(enderecoUsuario))
            throw new BadJpaArgumentException("O posto de saúde deve possuir endereço para ser armazenado no banco de dados.");
    }
}
