package com.boobiegoods.taskly.Data.Interfaces;

import com.boobiegoods.taskly.Domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IContratoRepository extends JpaRepository<Contrato, Integer> {
    
    //Buscar contratos por pessoa
    List<Contrato> findByPessoaId(Integer pessoaId);

    //Buscar contratos por perfil
    List<Contrato> findByPerfilId(Integer perfilId);

    //Buscar contratos por data de início
    List<Contrato> findByDataInicioContrato(LocalDate dataInicio);

    //Buscar contratos por data de fim
    List<Contrato> findByDataFimContrato(LocalDate dataFim);
    
    //Buscar contratos ativos (data fim >= hoje)
    @Query("SELECT c FROM Contrato c WHERE c.dataFimContrato >= :hoje")
    List<Contrato> findContratosAtivos(@Param("hoje") LocalDate hoje);
    
    //Buscar contratos finalizados (data fim < hoje)
    @Query("SELECT c FROM Contrato c WHERE c.dataFimContrato < :hoje")
    List<Contrato> findContratosFinalizados(@Param("hoje") LocalDate hoje);

    //Buscar contratos por período (data início entre duas datas)
    @Query("SELECT c FROM Contrato c WHERE c.dataInicioContrato BETWEEN :inicio AND :fim")
    List<Contrato> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
    List<Contrato> findByDataInicioContratoBetween(LocalDate dataInicio, LocalDate dataFim);

    //Buscar contratos por valor hora (intervalo)
    @Query("SELECT c FROM Contrato c WHERE c.salarioHora BETWEEN :valorMin AND :valorMax")
    List<Contrato> findByValorHoraBetween(@Param("valorMin") Double valorMin, @Param("valorMax") Double valorMax);

    //Buscar contratos por número de horas semanais
    List<Contrato> findByNumeroHorasSemana(Integer horas);

    //Buscar contratos por número de horas semanais (intervalo)
    List<Contrato> findByNumeroHorasSemanaBetween(Integer horasMin, Integer horasMax);

    //Buscar contratos que têm alocações
    @Query("SELECT DISTINCT c FROM Contrato c WHERE c.alocacoes IS NOT EMPTY")
    List<Contrato> findContratosComAlocacoes();

    //Buscar contratos sem alocações
    @Query("SELECT c FROM Contrato c WHERE c.alocacoes IS EMPTY")
    List<Contrato> findContratosSemAlocacoes();

    //Contar contratos ativos
    @Query("SELECT COUNT(c) FROM Contrato c WHERE c.dataFimContrato >= :hoje")
    long countContratosAtivos(@Param("hoje") LocalDate hoje);

    //Buscar contratos ordenados por data de início
    List<Contrato> findAllByOrderByDataInicioContratoAsc();

    //Buscar contratos ordenados por valor hora (decrescente)
    List<Contrato> findAllByOrderBySalarioHoraDesc();

    //Buscar contratos de uma pessoa por período
    @Query("SELECT c FROM Contrato c WHERE c.pessoa.id = :pessoaId AND c.dataInicioContrato BETWEEN :inicio AND :fim")
    List<Contrato> findByPessoaIdAndPeriodo(@Param("pessoaId") Integer pessoaId, 
                                           @Param("inicio") LocalDate inicio, 
                                           @Param("fim") LocalDate fim);
}
