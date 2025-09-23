package com.boobiegoods.taskly.API.DTO;

import com.boobiegoods.taskly.Domain.Projeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO Record com validação para transferência de dados de Projeto
 */
public record ProjetoDTO(
    Integer id,
    
    @NotBlank(message = "Nome do projeto é obrigatório")
    @Size(min = 3, max = 120, message = "Nome do projeto deve ter entre 3 e 120 caracteres")
    String nomeProjeto,
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    String descricaoProjeto,
    
    @NotNull(message = "Data de início é obrigatória")
    LocalDate dataInicioProjeto,
    
    LocalDate dataTerminoProjeto
) {
    // Constructor que recebe a entity Projeto
    public ProjetoDTO(Projeto projeto) {
        this(
            projeto.getId(),
            projeto.getNomeProjeto(),
            projeto.getDescricaoProjeto(),
            projeto.getDataInicioProjeto(),
            projeto.getDataTerminoProjeto()
        );
    }
}