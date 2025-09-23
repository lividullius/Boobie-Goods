package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Integer> {
    
    //Buscar perfil por tipo
    Optional<Perfil> findByTipo(TipoPerfil tipo);
    
    //Buscar perfis por tipo
    List<Perfil> findByTipoIn(List<TipoPerfil> tipos);

    //Verificar se perfil existe por tipo
    boolean existsByTipo(TipoPerfil tipo);
    
    //Buscar perfis que têm pessoas associadas
    @Query("SELECT DISTINCT p FROM Perfil p WHERE p.pessoas IS NOT EMPTY")
    List<Perfil> findPerfisComPessoas();
    
    //Buscar perfis sem pessoas associadas
    @Query("SELECT p FROM Perfil p WHERE p.pessoas IS EMPTY")
    List<Perfil> findPerfisSemPessoas();
    
    //Buscar perfis que têm contratos
    @Query("SELECT DISTINCT p FROM Perfil p WHERE p.contratos IS NOT EMPTY")
    List<Perfil> findPerfisComContratos();

    //Contar perfis por tipo
    long countByTipo(TipoPerfil tipo);

    //Buscar perfis ordenados por tipo
    List<Perfil> findAllByOrderByTipoAsc();

    //Buscar perfis de uma pessoa específica
    @Query("SELECT p FROM Perfil p JOIN p.pessoas pe WHERE pe.id = :pessoaId")
    List<Perfil> findByPessoaId(@Param("pessoaId") Integer pessoaId);
}
