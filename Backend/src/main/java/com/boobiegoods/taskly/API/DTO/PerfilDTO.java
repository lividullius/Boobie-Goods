package com.boobiegoods.taskly.API.DTO;

import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import jakarta.validation.constraints.NotNull;

/**
 * DTO Record com validação para transferência de dados de Perfil
 */
public record PerfilDTO(
    Integer id,
    
    @NotNull(message = "Tipo do perfil é obrigatório")
    TipoPerfil tipo
) {
    // Constructor que recebe a entity Perfil
    public PerfilDTO(Perfil perfil) {
        this(perfil.getId(), perfil.getTipo());
    }
}