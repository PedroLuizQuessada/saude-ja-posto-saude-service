package com.example.saudejapostosaudeservice.gateways;

import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.dtos.PacienteIdDtoPage;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDto;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDtoPage;
import com.example.saudejapostosaudeservice.dtos.ProfissionalSaudeIdDtoPage;
import com.example.saudejapostosaudeservice.entidades.PacienteIdPage;
import com.example.saudejapostosaudeservice.entidades.PostoSaude;
import com.example.saudejapostosaudeservice.entidades.PostoSaudePage;
import com.example.saudejapostosaudeservice.entidades.ProfissionalSaudeIdPage;
import com.example.saudejapostosaudeservice.exceptions.NaoEncontradoException;
import com.example.saudejapostosaudeservice.mappers.PostoSaudeMapper;
import enums.EstadoEnum;

import java.util.List;
import java.util.Optional;

public class PostoSaudeGateway {

    private final PostoSaudeDataSource postoSaudeDataSource;

    public PostoSaudeGateway(PostoSaudeDataSource postoSaudeDataSource) {
        this.postoSaudeDataSource = postoSaudeDataSource;
    }

    public PostoSaude savePostoSaude(PostoSaudeDto postoSaudeDto) {
        PostoSaudeDto postoSaudeDtoSalvo = postoSaudeDataSource.savePostoSaude(postoSaudeDto);
        return PostoSaudeMapper.toEntidade(postoSaudeDtoSalvo);
    }

    public Long countByNome(String nome) {
        return postoSaudeDataSource.countByNome(nome);
    }

    public PostoSaude getPostoSaudeById(Long id) {
        Optional<PostoSaudeDto> optionalPostoSaudeDto = postoSaudeDataSource.getPostoSaudeById(id);
        if (optionalPostoSaudeDto.isEmpty())
            throw new NaoEncontradoException(String.format("Posto saúde %d não encontrado.", id));
        return PostoSaudeMapper.toEntidade(optionalPostoSaudeDto.get());
    }

    public PostoSaudePage getPostoSaude(int page, int size, boolean ord, String cidade, EstadoEnum estado) {
        PostoSaudeDtoPage postoSaudeDtoPage = postoSaudeDataSource.getPostoSaude(page, size, ord, cidade, estado);
        List<PostoSaude> postoSaudeList = postoSaudeDtoPage.getContent().stream().map(PostoSaudeMapper::toEntidade).toList();
        return new PostoSaudePage(postoSaudeDtoPage.getPage(), postoSaudeDtoPage.getSize(), postoSaudeList);
    }

    public PacienteIdPage getPacienteList(int page, int size, boolean ord, Long postoSaudeId) {
        PacienteIdDtoPage pacienteIdDtoPage = postoSaudeDataSource.getPacienteList(page, size, ord, postoSaudeId);
        return new PacienteIdPage(pacienteIdDtoPage.getPage(), pacienteIdDtoPage.getSize(), pacienteIdDtoPage.getContent());
    }

    public ProfissionalSaudeIdPage getProfissionalSaudeList(int page, int size, boolean ord, Long postoSaudeId) {
        ProfissionalSaudeIdDtoPage profissionalSaudeIdDtoPage = postoSaudeDataSource.getProfissionalSaudeList(page, size, ord, postoSaudeId);
        return new ProfissionalSaudeIdPage(profissionalSaudeIdDtoPage.getPage(), profissionalSaudeIdDtoPage.getSize(), profissionalSaudeIdDtoPage.getContent());
    }

    public boolean isPostoSaudeContainsPaciente(Long pacienteId, Long postoSaudeId) {
        return postoSaudeDataSource.countByPacienteAndPostoSaude(pacienteId, postoSaudeId) != 0;
    }

    public boolean isPostoSaudeContainsProfissionalSaude(Long profissionalSaudeId, Long postoSaudeId) {
        return postoSaudeDataSource.countByProfissionalSaudeAndPostoSaude(profissionalSaudeId, postoSaudeId) != 0;
    }

    public void vincularPaciente(Long pacienteId, Long postoSaudeId) {
        postoSaudeDataSource.vincularPaciente(pacienteId, postoSaudeId);
    }

    public void removerPaciente(Long pacienteId, Long postoSaudeId) {
        postoSaudeDataSource.removerPaciente(pacienteId, postoSaudeId);
    }

    public void vincularProfissionalSaudePostoSaude(Long profissionalSaude, Long postoSaudeId) {
        postoSaudeDataSource.vincularProfissionalSaudePostoSaude(profissionalSaude, postoSaudeId);
    }

    public void removerProfissionalSaude(Long profissionalSaudeId, Long postoSaudeId) {
        postoSaudeDataSource.removerProfissionalSaude(profissionalSaudeId, postoSaudeId);
    }

    public void deletePostoSaudeById(Long postoSaudeId) {
        postoSaudeDataSource.deletePostoSaudeById(postoSaudeId);
    }
}
