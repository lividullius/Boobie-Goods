package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Alocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAlocacaoRepository extends JpaRepository<Alocacao, Integer> {

    // ------- finders básicos -------
    List<Alocacao> findByProjetoId(Integer projetoId);

    List<Alocacao> findByPessoaId(Integer pessoaId);

    List<Alocacao> findByContratoId(Integer contratoId);

    Optional<Alocacao> findByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId);

    List<Alocacao> findByHorasSemanal(Integer horas);

    List<Alocacao> findByHorasSemanalBetween(Integer horasMin, Integer horasMax);

    boolean existsByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId);

    long countByProjetoId(Integer projetoId);

    long countByPessoaId(Integer pessoaId);
    
    List<Alocacao> findAllByOrderByHorasSemanalDesc();

    @Query("SELECT COALESCE(SUM(a.horasSemanal), 0) FROM Alocacao a WHERE a.projeto.id = :projetoId")
    Integer sumHorasSemanalByProjetoId(@Param("projetoId") Integer projetoId);

    @Query("SELECT COALESCE(SUM(a.horasSemanal), 0) FROM Alocacao a WHERE a.pessoa.id = :pessoaId")
    Integer sumHorasSemanalByPessoaId(@Param("pessoaId") Integer pessoaId);

    @Query("""
           SELECT a FROM Alocacao a WHERE a.pessoa.id IN
           (SELECT a2.pessoa.id FROM Alocacao a2 GROUP BY a2.pessoa.id HAVING SUM(a2.horasSemanal) > :maxHoras)
           """)
    List<Alocacao> findAlocacoesPessoasComExcessoHoras(@Param("maxHoras") Integer maxHoras);

    @Query("""
           SELECT a FROM Alocacao a WHERE a.projeto.id IN
           (SELECT a2.projeto.id FROM Alocacao a2 GROUP BY a2.projeto.id HAVING SUM(a2.horasSemanal) > :maxHoras)
           """)
    List<Alocacao> findAlocacoesProjetosComExcessoHoras(@Param("maxHoras") Integer maxHoras);

    List<Alocacao> findByProjetoIdAndContratoId(Integer projetoId, Integer contratoId);

    
    @Query("""
        SELECT a
        FROM Alocacao a
          JOIN a.projeto p
          JOIN FETCH a.contrato c
        WHERE p.id = :projetoId
          AND p.dataInicioProjeto <= :fim
          AND (p.dataTerminoProjeto IS NULL OR p.dataTerminoProjeto >= :inicio)
          AND c.dataInicioContrato <= :fim
          AND (c.dataFimContrato IS NULL OR c.dataFimContrato >= :inicio)
        """)
    List<Alocacao> findByProjetoAndPeriodoOverlapFetchContrato(
            @Param("projetoId") Integer projetoId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    // Opcional: buscar todas do projeto já com CONTRATO carregado
    @Query("SELECT a FROM Alocacao a JOIN FETCH a.contrato c WHERE a.projeto.id = :projetoId")
    List<Alocacao> findByProjetoFetchContrato(@Param("projetoId") Integer projetoId);
}
