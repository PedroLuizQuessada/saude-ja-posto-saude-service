package com.example.saudejapostosaudeservice.dtos;

import dtos.abstrato.Page;

import java.util.List;

public class PostoSaudeDtoPage extends Page<PostoSaudeDto> {
    public PostoSaudeDtoPage(int page, int size, List<PostoSaudeDto> content) {
        super(page, size, content);
    }
}
