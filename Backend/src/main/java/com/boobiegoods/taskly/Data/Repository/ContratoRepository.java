package com.boobiegoods.taskly.Data.Repository;

import com.boobiegoods.taskly.Data.Interfaces.IContratoRepository;
import com.boobiegoods.taskly.Domain.Contrato;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de banco de dados da entidade Contrato
 * Como estendemos JpaRepository através da interface IContratoRepository,
 * o Spring automaticamente fornece implementações para todos os métodos CRUD básicos
 * e os métodos customizados definidos na interface.
 */
@Repository
public interface ContratoRepository extends IContratoRepository {

    List<Contrato> findAllByOrderBySalarioHoraAsc();
    // Não precisamos implementar nada aqui, pois o Spring Data JPA
    // fornece automaticamente todas as implementações baseadas nos
    // nomes dos métodos definidos na interface IContratoRepository
}
