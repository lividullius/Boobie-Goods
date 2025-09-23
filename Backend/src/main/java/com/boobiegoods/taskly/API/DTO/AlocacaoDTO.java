package com.boobiegoods.taskly.API.DTO;

import com.boobiegoods.taskly.Domain.Alocacao;
import jakarta.validation.constraints.*;

/**
 * DTO Record com validação para transferência de dados de Alocação
 */
public record AlocacaoDTO(
    Integer idAlocacao,
    
    @NotNull(message = "ID do projeto é obrigatório")
    Integer projetoId,
    
    String projetoNome, // Preenchido automaticamente
    
    @NotNull(message = "ID do contrato é obrigatório")
    Integer contratoId,
    
    Integer pessoaId, // Preenchido automaticamente
    String pessoaNome, // Preenchido automaticamente
    String perfilTipo, // Preenchido automaticamente
    
    @NotNull(message = "Horas semanais são obrigatórias")
    @Min(value = 1, message = "Deve alocar pelo menos 1 hora por semana")
    @Max(value = 40, message = "Não pode alocar mais de 40 horas por semana")
    Integer horasSemanal,
    
    Double valorHora // Preenchido automaticamente do contrato
) {
    // Constructor que recebe a entity Alocacao
    public AlocacaoDTO(Alocacao alocacao) {
        this(
            alocacao.getIdAlocacao(),
            alocacao.getProjeto().getId(),
            alocacao.getProjeto().getNomeProjeto(),
            alocacao.getContrato().getId(),
            alocacao.getPessoa().getId(),
            alocacao.getPessoa().getNome(),
            alocacao.getContrato().getPerfil().getTipo().toString(),
            alocacao.getHorasSemanal(),
            alocacao.getContrato().getValorHora()
        );
    }
}