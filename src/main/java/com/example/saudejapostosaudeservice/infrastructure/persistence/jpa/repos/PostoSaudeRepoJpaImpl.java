package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.repos;

import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.dtos.PacienteIdDtoPage;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDto;
import com.example.saudejapostosaudeservice.dtos.PostoSaudeDtoPage;
import com.example.saudejapostosaudeservice.dtos.ProfissionalSaudeIdDtoPage;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers.EnderecoPostoSaudeJpaDtoMapper;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers.PostoSaudeJpaDtoMapper;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models.PostoSaudeJpa;
import enums.EstadoEnum;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("jpa")
public class PostoSaudeRepoJpaImpl implements PostoSaudeDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostoSaudeJpaDtoMapper postoSaudeJpaDtoMapper;

    @Autowired
    private EnderecoPostoSaudeJpaDtoMapper enderecoPostoSaudeJpaDtoMapper;

    @Override
    public Long countByNome(String nome) {
        Query query = entityManager.createQuery("SELECT count(*) FROM PostoSaudeJpa postoSaude WHERE postoSaude.nome = :nome");
        query.setParameter("nome", nome);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public PostoSaudeDto savePostoSaude(PostoSaudeDto postoSaudeDto) {
        if (Objects.isNull(postoSaudeDto.id())) {
            PostoSaudeJpa postoSaudeJpa = postoSaudeJpaDtoMapper.toJpa(postoSaudeDto);
            entityManager.merge(postoSaudeJpa);
        }
        else {
            Query query = entityManager.createQuery("UPDATE PostoSaudeJpa postoSaude SET postoSaude.nome = :nome, postoSaude.enderecoPostoSaude = :enderecoPostoSaude WHERE postoSaude.id = :id");
            query.setParameter("nome", postoSaudeDto.nome());
            query.setParameter("enderecoPostoSaude", enderecoPostoSaudeJpaDtoMapper.toJpa(postoSaudeDto.enderecoPostoSaudeDto()));
            query.setParameter("id", postoSaudeDto.id());
            query.executeUpdate();
        }

        return postoSaudeDto;
    }

    @Override
    public Optional<PostoSaudeDto> getPostoSaudeById(Long id) {
        Query query = entityManager.createQuery("SELECT postoSaude FROM PostoSaudeJpa postoSaude WHERE postoSaude.id = :id");
        query.setParameter("id", id);
        try {
            PostoSaudeJpa postoSaudeJpa = (PostoSaudeJpa) query.getSingleResult();
            return Optional.of(postoSaudeJpaDtoMapper.toDto(postoSaudeJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public PostoSaudeDtoPage getPostoSaude(int page, int size, boolean ord, String cidade, EstadoEnum estado) {
        int offset = page * size;

        String jpql =
                "SELECT p FROM PostoSaudeJpa p " +
                        "WHERE (:cidade IS NULL OR UPPER(p.enderecoPostoSaude.cidade) LIKE UPPER(CONCAT('%', :cidade, '%'))) " +
                        "AND (:estado IS NULL OR p.enderecoPostoSaude.estado = :estado) " +
                        "ORDER BY p.id " + (ord ? "ASC" : "DESC");

        TypedQuery<PostoSaudeJpa> query = entityManager
                .createQuery(jpql, PostoSaudeJpa.class)
                .setParameter("cidade", cidade)
                .setParameter("estado", estado)
                .setFirstResult(offset)
                .setMaxResults(size);

        return new PostoSaudeDtoPage(
                page,
                size,
                query.getResultList()
                        .stream()
                        .map(postoSaudeJpaDtoMapper::toDto)
                        .toList()
        );
    }

    @Override
    public PacienteIdDtoPage getPacienteList(int page, int size, boolean ord, Long postoSaudeId) {
        int offset = page * size;

        String jpql =
                "SELECT p.pacienteList FROM PostoSaudeJpa p " +
                        "WHERE p.id = :postoSaudeId " +
                        "ORDER BY p.id " + (ord ? "ASC" : "DESC");

        TypedQuery<Long> query = entityManager
                .createQuery(jpql, Long.class)
                .setParameter("postoSaudeId", postoSaudeId)
                .setFirstResult(offset)
                .setMaxResults(size);

        return new PacienteIdDtoPage(
                page,
                size,
                query.getResultList()
        );
    }

    @Override
    public ProfissionalSaudeIdDtoPage getProfissionalSaudeList(int page, int size, boolean ord, Long postoSaudeId) {
        int offset = page * size;

        String jpql =
                "SELECT p.profissionalSaudeList FROM PostoSaudeJpa p " +
                        "WHERE p.id = :postoSaudeId " +
                        "ORDER BY p.id " + (ord ? "ASC" : "DESC");

        TypedQuery<Long> query = entityManager
                .createQuery(jpql, Long.class)
                .setParameter("postoSaudeId", postoSaudeId)
                .setFirstResult(offset)
                .setMaxResults(size);

        return new ProfissionalSaudeIdDtoPage(
                page,
                size,
                query.getResultList()
        );
    }

    @Override
    public Long countByPacienteAndPostoSaude(Long pacienteId, Long postoSaudeId) {
        String sql = """
        SELECT count(*) FROM posto_pacientes
        WHERE paciente_id = :pacienteId AND  posto_id = :postoSaudeId
    """;

        return (Long) entityManager.createNativeQuery(sql)
                .setParameter("pacienteId", pacienteId)
                .setParameter("postoSaudeId", postoSaudeId)
                .getSingleResult();
    }

    @Override
    public Long countProfissionaisSaudeByPostoSaude(Long postoSaudeId) {
        String sql = """
        SELECT count(*) FROM posto_profissionais_saude
        WHERE posto_id = :postoSaudeId
    """;

        return (Long) entityManager.createNativeQuery(sql)
                .setParameter("postoSaudeId", postoSaudeId)
                .getSingleResult();
    }

    @Override
    public Long countByProfissionalSaudeAndPostoSaude(Long profissionalSaudeId, Long postoSaudeId) {
        String sql = """
        SELECT count(*) FROM posto_profissionais_saude
        WHERE profissional_saude_id = :profissionalSaudeId AND  posto_id = :postoSaudeId
    """;

        return (Long) entityManager.createNativeQuery(sql)
                .setParameter("profissionalSaudeId", profissionalSaudeId)
                .setParameter("postoSaudeId", postoSaudeId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void vincularPaciente(Long pacienteId, Long postoSaudeId) {
        PostoSaudeJpa postoSaudeJpa = entityManager.find(PostoSaudeJpa.class, postoSaudeId);
        postoSaudeJpa.getPacienteList().add(pacienteId);
    }

    @Override
    @Transactional
    public void removerPaciente(Long pacienteId, Long postoSaudeId) {
        PostoSaudeJpa postoSaudeJpa = entityManager.find(PostoSaudeJpa.class, postoSaudeId);
        postoSaudeJpa.getPacienteList().remove(pacienteId);
    }

    @Override
    @Transactional
    public void removerPaciente(Long pacienteId) {
        String sql = """
        DELETE FROM posto_pacientes
        WHERE paciente_id = :pacienteId
    """;

        entityManager.createNativeQuery(sql)
                .setParameter("pacienteId", pacienteId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void vincularProfissionalSaudePostoSaude(Long profissionalSaude, Long postoSaudeId) {
        PostoSaudeJpa postoSaudeJpa = entityManager.find(PostoSaudeJpa.class, postoSaudeId);
        postoSaudeJpa.getProfissionalSaudeList().add(profissionalSaude);
    }

    @Override
    @Transactional
    public void removerProfissionalSaude(Long profissionalSaude, Long postoSaudeId) {
        PostoSaudeJpa postoSaudeJpa = entityManager.find(PostoSaudeJpa.class, postoSaudeId);
        postoSaudeJpa.getProfissionalSaudeList().remove(profissionalSaude);
    }

    @Override
    public void removerProfissionalSaude(Long profissionalSaude) {
        String sql = """
        DELETE FROM posto_profissionais_saude
        WHERE profissional_saude_id = :profissionalSaude
    """;

        entityManager.createNativeQuery(sql)
                .setParameter("profissionalSaude", profissionalSaude)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deletePostoSaudeById(Long postoSaudeId) {
        PostoSaudeJpa postoSaudeJpa = entityManager.find(PostoSaudeJpa.class, postoSaudeId);
        if (postoSaudeJpa != null) {
            entityManager.remove(postoSaudeJpa);
        }
    }
}
