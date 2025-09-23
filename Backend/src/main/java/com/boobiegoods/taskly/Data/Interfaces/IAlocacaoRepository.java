package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Alocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlocacaoRepository extends JpaRepository<Alocacao, Integer> {
    
    //Buscar alocações por projeto
    List<Alocacao> findByProjetoId(Integer projetoId);

    //Buscar alocações por pessoa
    List<Alocacao> findByPessoaId(Integer pessoaId);
    
    //Buscar alocações por contrato
    List<Alocacao> findByContratoId(Integer contratoId);
    
    //Buscar alocação específica por projeto e pessoa
    Optional<Alocacao> findByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId);
    
    //Buscar alocações por número de horas semanais
    List<Alocacao> findByHorasSemanal(Integer horas);

    //Buscar alocações por intervalo de horas semanais
    List<Alocacao> findByHorasSemanalBetween(Integer horasMin, Integer horasMax);

    //Verificar se pessoa já está alocada em um projeto
    boolean existsByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId);
    
    //Calcular total de horas semanais de um projeto
    @Query("SELECT COALESCE(SUM(a.horasSemanal), 0) FROM Alocacao a WHERE a.projeto.id = :projetoId")
    Integer sumHorasSemanalByProjetoId(@Param("projetoId") Integer projetoId);
    
    //Calcular total de horas semanais de uma pessoa
    @Query("SELECT COALESCE(SUM(a.horasSemanal), 0) FROM Alocacao a WHERE a.pessoa.id = :pessoaId")
    Integer sumHorasSemanalByPessoaId(@Param("pessoaId") Integer pessoaId);

    //Contar alocações por projeto
    long countByProjetoId(Integer projetoId);

    //Contar alocações por pessoa
    long countByPessoaId(Integer pessoaId);
    
    //Buscar alocações ordenadas por horas semanais (decrescente)
    List<Alocacao> findAllByOrderByHorasSemanalDesc();
    
    //Buscar pessoas que excedem determinado número de horas
    @Query("SELECT a FROM Alocacao a WHERE a.pessoa.id IN " +
           "(SELECT a2.pessoa.id FROM Alocacao a2 GROUP BY a2.pessoa.id HAVING SUM(a2.horasSemanal) > :maxHoras)")
    List<Alocacao> findAlocacoesPessoasComExcessoHoras(@Param("maxHoras") Integer maxHoras);

    //Buscar projetos que excedem determinado número de horas
    @Query("SELECT a FROM Alocacao a WHERE a.projeto.id IN " +
           "(SELECT a2.projeto.id FROM Alocacao a2 GROUP BY a2.projeto.id HAVING SUM(a2.horasSemanal) > :maxHoras)")
    List<Alocacao> findAlocacoesProjetosComExcessoHoras(@Param("maxHoras") Integer maxHoras);

    //Buscar alocações de um projeto por contrato
    List<Alocacao> findByProjetoIdAndContratoId(Integer projetoId, Integer contratoId);
}
