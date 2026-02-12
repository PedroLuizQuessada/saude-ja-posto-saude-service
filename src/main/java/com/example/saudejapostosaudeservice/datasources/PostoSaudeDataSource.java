package com.example.saudejapostosaudeservice.datasources;

import com.example.saudejapostosaudeservice.dtos.PacienteIdDtoPage;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDto;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDtoPage;
import com.example.saudejapostosaudeservice.dtos.ProfissionalSaudeIdDtoPage;
import enums.EstadoEnum;

import java.util.Optional;

public interface PostoSaudeDataSource {
    Long countByNome(String nome);
    PostoSaudeDto savePostoSaude(PostoSaudeDto postoSaudeDto);
    Optional<PostoSaudeDto> getPostoSaudeById(Long id);
    PostoSaudeDtoPage getPostoSaude(int page, int size, boolean ord, String cidade, EstadoEnum estado);
    PacienteIdDtoPage getPacienteList(int page, int size, boolean ord, Long postoSaudeId);
    ProfissionalSaudeIdDtoPage getProfissionalSaudeList(int page, int size, boolean ord, Long postoSaudeId);
    Long countByPacienteAndPostoSaude(Long pacienteId, Long postoSaudeId);
    Long countByProfissionalSaudeAndPostoSaude(Long profissionalSaudeId, Long postoSaudeId);
    void vincularPaciente(Long pacienteId, Long postoSaudeId);
    void removerPaciente(Long pacienteId, Long postoSaudeId);
    void removerPaciente(Long pacienteId);
    void vincularProfissionalSaudePostoSaude(Long profissionalSaude, Long postoSaudeId);
    void removerProfissionalSaude(Long profissionalSaude, Long postoSaudeId);
    void removerProfissionalSaude(Long profissionalSaude);
    void deletePostoSaudeById(Long postoSaudeId);
}
