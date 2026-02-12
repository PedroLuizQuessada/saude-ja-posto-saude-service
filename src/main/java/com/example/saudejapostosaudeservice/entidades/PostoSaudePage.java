package com.example.saudejapostosaudeservice.entidades;

import com.example.saudejapostosaudeservice.exceptions.BadArgumentException;
import dtos.abstrato.Page;

import java.util.List;
import java.util.Objects;

public class PostoSaudePage extends Page<PostoSaude> {
    public PostoSaudePage(int page, int size, List<PostoSaude> content) {
        super(page, size, content);
        validarPostoSaude(content);
    }

    private void validarPostoSaude(List<PostoSaude> content) {
        content.forEach(postoSaude -> {
            if (Objects.isNull(postoSaude)) {
                throw new BadArgumentException("O posto de saúde não deve ser nulo.");
            }
        });
    }
}
