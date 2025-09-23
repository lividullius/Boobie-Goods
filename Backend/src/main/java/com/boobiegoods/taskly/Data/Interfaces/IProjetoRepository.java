package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProjetoRepository extends JpaRepository<Projeto, Integer> {
    
    /* Buscar projeto por nome */
    Optional<Projeto> findByNomeProjeto(String nomeProjeto);
    
    /* Buscar projetos que contêm determinado texto no nome */
    List<Projeto> findByNomeProjetoContainingIgnoreCase(String nome);
    
    /* Buscar projetos por data de início */
    List<Projeto> findByDataInicioProjeto(LocalDate dataInicio);
    
    /* Buscar projetos por data de término */
    List<Projeto> findByDataTerminoProjeto(LocalDate dataTermino);
    
    /* Buscar projetos ativos (data término >= hoje ou data término nula) */
    @Query("SELECT p FROM Projeto p WHERE p.dataTerminoProjeto IS NULL OR p.dataTerminoProjeto >= :hoje")
    List<Projeto> findProjetosAtivos(@Param("hoje") LocalDate hoje);
    
    /* Buscar projetos finalizados (data término < hoje) */
    @Query("SELECT p FROM Projeto p WHERE p.dataTerminoProjeto IS NOT NULL AND p.dataTerminoProjeto < :hoje")
    List<Projeto> findProjetosFinalizados(@Param("hoje") LocalDate hoje);
    
    /* Buscar projetos por período (data início entre duas datas) */
    List<Projeto> findByDataInicioProjetoBetween(LocalDate dataInicio, LocalDate dataFim);
    
    /* Buscar projetos ordenados por data de início */
    List<Projeto> findAllByOrderByDataInicioProjetoAsc();
    
    /* Buscar projetos ordenados por data de término */
    List<Projeto> findAllByOrderByDataTerminoProjetoAsc();

    /* Contar projetos ativos */
    @Query("SELECT COUNT(p) FROM Projeto p WHERE p.dataTerminoProjeto IS NULL OR p.dataTerminoProjeto >= :hoje")
    long countProjetosAtivos(@Param("hoje") LocalDate hoje);
    
    /* Buscar projetos que têm alocações */
    @Query("SELECT DISTINCT p FROM Projeto p JOIN p.alocacoes a")
    List<Projeto> findProjetosComAlocacoes();

    /* Buscar projetos sem alocações */
    @Query("SELECT p FROM Projeto p WHERE p.alocacoes IS EMPTY")
    List<Projeto> findProjetosSemAlocacoes();

    /* Verificar se projeto existe por nome (ignorando case) */
    boolean existsByNomeProjetoIgnoreCase(String nomeProjeto);

    /* Buscar projetos por descrição contendo texto */
    List<Projeto> findByDescricaoProjetoContainingIgnoreCase(String descricao);
}
