package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Pessoa;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Integer> {
    
    // Buscar pessoa por nome
    Optional<Pessoa> findByNome(String nome);
    
    // Buscar pessoas que contêm determinado texto no nome
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    
    // Verificar se pessoa existe por nome (ignorando case)
    boolean existsByNomeIgnoreCase(String nome);
    
    // Buscar pessoas por perfil
    @Query("SELECT DISTINCT p FROM Pessoa p JOIN p.perfis pf WHERE pf.id = :perfilId")
    List<Pessoa> findByPerfilId(@Param("perfilId") Integer perfilId);
    
    // Buscar pessoas que têm contratos
    @Query("SELECT DISTINCT p FROM Pessoa p WHERE p.contratos IS NOT EMPTY")
    List<Pessoa> findPessoasComContratos();
    
    // Buscar pessoas sem contratos
    @Query("SELECT p FROM Pessoa p WHERE p.contratos IS EMPTY")
    List<Pessoa> findPessoasSemContratos();
    
    // Buscar pessoas que têm alocações
    @Query("SELECT DISTINCT p FROM Pessoa p WHERE p.alocacoes IS NOT EMPTY")
    List<Pessoa> findPessoasComAlocacoes();
    
    // Buscar pessoas alocadas em um projeto específico
    @Query("SELECT DISTINCT p FROM Pessoa p JOIN p.alocacoes a WHERE a.projeto.id = :projetoId")
    List<Pessoa> findByProjetoId(@Param("projetoId") Integer projetoId);
    
    // Contar pessoas com contratos ativos
    @Query("SELECT COUNT(DISTINCT p) FROM Pessoa p JOIN p.contratos c WHERE c.dataFimContrato >= CURRENT_DATE")
    long countPessoasComContratosAtivos();
    
    // Buscar pessoas ordenadas por nome
    List<Pessoa> findAllByOrderByNomeAsc();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (:pessoaId, :perfilId)", nativeQuery = true)
    void associarPerfil(@Param("pessoaId") Integer pessoaId, @Param("perfilId") Integer perfilId);
}

