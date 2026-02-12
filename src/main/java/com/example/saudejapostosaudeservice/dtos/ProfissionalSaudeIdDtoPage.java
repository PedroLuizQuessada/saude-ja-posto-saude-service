package com.example.saudejapostosaudeservice.dtos;

import dtos.abstrato.Page;

import java.util.List;

public class ProfissionalSaudeIdDtoPage extends Page<Long> {
    public ProfissionalSaudeIdDtoPage(int page, int size, List<Long> content) {
        super(page, size, content);
    }
}
