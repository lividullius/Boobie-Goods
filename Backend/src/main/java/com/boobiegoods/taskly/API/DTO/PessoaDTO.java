package com.boobiegoods.taskly.API.DTO;

import com.boobiegoods.taskly.Domain.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO Record com validação para transferência de dados de Pessoa
 */
public record PessoaDTO(
    Integer id,
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome
) {
    // Constructor que recebe a entity Pessoa
    public PessoaDTO(Pessoa pessoa) {
        this(pessoa.getId(), pessoa.getNome());
    }
}