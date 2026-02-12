package com.example.saudejapostosaudeservice.entidades;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import dtos.abstrato.Page;

import java.util.List;
import java.util.Objects;

public class ProfissionalSaudeIdPage extends Page<Long> {
    public ProfissionalSaudeIdPage(int page, int size, List<Long> content) {
        super(page, size, content);
        validarProfissionalSaudeId(content);
    }

    private void validarProfissionalSaudeId(List<Long> content) {
        content.forEach(profissionalSaudeId -> {
            if (Objects.isNull(profissionalSaudeId)) {
                throw new BadArgumentException("O profissional da saúde deve possuir um ID.");
            }
        });
    }
}
