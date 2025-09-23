package com.boobiegoods.taskly.API.DTO;

import com.boobiegoods.taskly.Domain.Contrato;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO Record com validação para transferência de dados de Contrato
 */
public record ContratoDTO(
    Integer id,
    
    @NotNull(message = "ID da pessoa é obrigatório")
    Integer pessoaId,
    
    String pessoaNome, // Preenchido automaticamente, não precisa validar
    
    @NotNull(message = "ID do perfil é obrigatório")
    Integer perfilId,
    
    String perfilTipo, // Preenchido automaticamente, não precisa validar
    
    @NotNull(message = "Data de início do contrato é obrigatória")
    LocalDate dataInicioContrato,
    
    LocalDate dataFimContrato,
    
    @NotNull(message = "Número de horas por semana é obrigatório")
    @Min(value = 1, message = "Deve ter pelo menos 1 hora por semana")
    @Max(value = 40, message = "Não pode exceder 40 horas por semana")
    Integer numeroHorasSemana,
    
    @NotNull(message = "Valor por hora é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor por hora deve ser maior que zero")
    @DecimalMax(value = "9999.99", message = "Valor por hora não pode exceder R$ 9.999,99")
    Double valorHora
) {
    // Constructor que recebe a entity Contrato
    public ContratoDTO(Contrato contrato) {
        this(
            contrato.getId(),
            contrato.getPessoa().getId(),
            contrato.getPessoa().getNome(),
            contrato.getPerfil().getId(),
            contrato.getPerfil().getTipo().toString(),
            contrato.getDataInicioContrato(),
            contrato.getDataFimContrato(),
            contrato.getNumeroHorasSemana(),
            contrato.getValorHora()
        );
    }
}