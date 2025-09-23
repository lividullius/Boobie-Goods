package com.boobiegoods.taskly.API.DTO;

public class PerfilDTO {
    private int id;
    private String tipo; // representando o Enum como String para facilitar transporte de dados

    // Construtor padrão
    public PerfilDTO() {}

    // Construtor cheio
    public PerfilDTO(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
