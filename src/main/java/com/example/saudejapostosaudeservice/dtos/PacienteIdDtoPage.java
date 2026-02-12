package com.example.saudejapostosaudeservice.dtos;

import dtos.abstrato.Page;

import java.util.List;

public class PacienteIdDtoPage extends Page<Long> {
    public PacienteIdDtoPage(int page, int size, List<Long> content) {
        super(page, size, content);
    }
}
