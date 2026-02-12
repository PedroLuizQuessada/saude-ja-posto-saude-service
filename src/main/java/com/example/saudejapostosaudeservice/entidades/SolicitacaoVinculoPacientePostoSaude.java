package com.example.saudejapostosaudeservice.entidades;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class SolicitacaoVinculoPacientePostoSaude {
    private final Long id;
    private final Long paciente;
    private final Long postoSaude;
    @Setter
    private boolean consumida;

    public SolicitacaoVinculoPacientePostoSaude(Long id, Long paciente, Long postoSaude, boolean consumida) {
        String mensagemErro = "";

        try {
            validarPaciente(paciente);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarPostoSaude(postoSaude);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        this.id = id;
        this.paciente = paciente;
        this.postoSaude = postoSaude;
        this.consumida = consumida;
    }

    private void validarPaciente(Long paciente) {
        if (Objects.isNull(paciente))
            throw new BadArgumentException("A solicitação de vínculo do paciente a um posto de saúde deve possuir um paciente.");
    }

    private void validarPostoSaude(Long postoSaude) {
        if (Objects.isNull(postoSaude))
            throw new BadArgumentException("A solicitação de vínculo do paciente a um posto de saúde deve possuir um posto de saúde.");
    }
}
