package com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.repos;

import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.dtos.SolicitacaoVinculoPacientePostoSaudeDto;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.mappers.SolicitacaoVinculoPacientePostoSaudeJpaDtoMapper;
import com.example.saudejapostosaudeservice.infrastructure.persistence.jpa.models.SolicitacaoVinculoPacientePostoSaudeJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("jpa")
public class SolicitacaoContaUsuarioRepoJpaImpl implements SolicitacaoVinculoPacientePostoSaudeDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SolicitacaoVinculoPacientePostoSaudeJpaDtoMapper solicitacaoVinculoPacientePostoSaudeJpaDtoMapper;

    @Override
    public Long countByPacienteAndPostoSaudeWhereNotConsumida(Long paciente, Long postoSaude) {
        Query query = entityManager.createQuery("SELECT count(*) " +
                "FROM SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaude " +
                "WHERE solicitacaoVinculoPacientePostoSaude.paciente = :paciente " +
                " AND solicitacaoVinculoPacientePostoSaude.postoSaude = :postoSaude ");
        query.setParameter("paciente", paciente);
        query.setParameter("postoSaude", postoSaude);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public SolicitacaoVinculoPacientePostoSaudeDto criarSolicitacaoVinculoPacientePostoSaude(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaudeJpa = solicitacaoVinculoPacientePostoSaudeJpaDtoMapper.toJpa(solicitacaoVinculoPacientePostoSaudeDto);
        solicitacaoVinculoPacientePostoSaudeJpa = entityManager.merge(solicitacaoVinculoPacientePostoSaudeJpa);
        return solicitacaoVinculoPacientePostoSaudeJpaDtoMapper.toDto(solicitacaoVinculoPacientePostoSaudeJpa);
    }

    @Override
    public Optional<SolicitacaoVinculoPacientePostoSaudeDto> getSolicitacaoVinculoPacientePostoSaudeDtoById(Long id) {
        Query query = entityManager.createQuery("SELECT solicitacaoVinculoPacientePostoSaude FROM SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaude WHERE solicitacaoVinculoPacientePostoSaude.id = :id");
        query.setParameter("id", id);
        try {
            SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaudeJpa = (SolicitacaoVinculoPacientePostoSaudeJpa) query.getSingleResult();
            return Optional.of(solicitacaoVinculoPacientePostoSaudeJpaDtoMapper.toDto(solicitacaoVinculoPacientePostoSaudeJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void consumirSolicitacaoContaUsuario(SolicitacaoVinculoPacientePostoSaudeDto solicitacaoVinculoPacientePostoSaudeDto) {
        SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaudeJpa = solicitacaoVinculoPacientePostoSaudeJpaDtoMapper.toJpa(solicitacaoVinculoPacientePostoSaudeDto);
        entityManager.merge(solicitacaoVinculoPacientePostoSaudeJpa);
    }

    @Override
    @Transactional
    public void deleteByPostoSaudeId(Long postoSaudeId) {
        Query query = entityManager.createQuery("DELETE FROM SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaude WHERE solicitacaoVinculoPacientePostoSaude.postoSaude = :postoSaude");
        query.setParameter("postoSaude", postoSaudeId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteByPacienteId(Long pacienteId) {
        Query query = entityManager.createQuery("DELETE FROM SolicitacaoVinculoPacientePostoSaudeJpa solicitacaoVinculoPacientePostoSaude WHERE solicitacaoVinculoPacientePostoSaude.paciente = :pacienteId");
        query.setParameter("pacienteId", pacienteId);
        query.executeUpdate();
    }
}
