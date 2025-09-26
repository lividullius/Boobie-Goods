package com.boobiegoods.taskly.Data.Interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.Pessoa;

import jakarta.transaction.Transactional;

@Repository
public interface IPessoaPerfilRepository extends JpaRepository<Pessoa, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (:pessoaId, :perfilId)", nativeQuery = true)
    void associarPerfil(@Param("pessoaId") Integer pessoaId, @Param("perfilId") Integer perfilId);

    @Query(value = "SELECT p.* FROM Perfil p JOIN PessoaPerfil pp ON p.IDPerfil = pp.IDPerfil WHERE pp.IDPessoa = :pessoaId", nativeQuery = true)
    List<Perfil> listarPerfisDaPessoa(@Param("pessoaId") Integer pessoaId);
}



