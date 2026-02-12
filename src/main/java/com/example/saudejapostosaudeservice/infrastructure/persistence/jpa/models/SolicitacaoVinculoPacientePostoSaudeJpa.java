package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models;

import com.example.saudejapostosaudeservice.infrastructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Entity
@Table(name = "solicitacoes_vinculo_paciente_posto_saude")
@Getter
@NoArgsConstructor
@Profile("jpa")
public class SolicitacaoVinculoPacientePostoSaudeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long paciente;

    @Column(nullable = false, name = "posto_saude")
    private Long postoSaude;

    @Column(nullable = false)
    private Boolean consumida;

    public SolicitacaoVinculoPacientePostoSaudeJpa(Long id, Long paciente, Long postoSaude, Boolean consumida) {
        String message = "";

        try {
            validarPaciente(paciente);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarPostoSaude(postoSaude);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validarConsumida(consumida);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.paciente = paciente;
        this.postoSaude = postoSaude;
        this.consumida = consumida;
    }

    private void validarPaciente(Long paciente) {
        if (Objects.isNull(paciente))
            throw new BadJpaArgumentException("A solicitação de vínculo de paciente em posto de saúde deve possuir um paciente para ser armazenada no banco de dados.");
    }

    private void validarPostoSaude(Long postoSaude) {
        if (Objects.isNull(postoSaude))
            throw new BadJpaArgumentException("A solicitação de vínculo de paciente em posto de saúde deve possuir um posto de saúde para ser armazenada no banco de dados.");
    }

    private void validarConsumida(Boolean consumida) {
        if (Objects.isNull(consumida))
            throw new BadJpaArgumentException("A solicitação de vínculo de paciente em posto de saúde deve possuir um um indicativo de se foi consumida para ser armazenada no banco de dados.");
    }
}
