package com.boobiegoods.taskly.Data.Repository;

import com.boobiegoods.taskly.Data.Interfaces.IProjetoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de banco de dados da entidade Projeto
 * Como estendemos JpaRepository através da interface IProjetoRepository,
 * o Spring automaticamente fornece implementações para todos os métodos CRUD básicos
 * e os métodos customizados definidos na interface.
 */
@Repository
public interface ProjetoRepository extends IProjetoRepository {
    // Não precisamos implementar nada aqui, pois o Spring Data JPA
    // fornece automaticamente todas as implementações baseadas nos
    // nomes dos métodos definidos na interface IProjetoRepository
}
