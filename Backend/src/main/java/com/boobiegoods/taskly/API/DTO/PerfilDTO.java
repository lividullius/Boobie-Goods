package com.boobiegoods.taskly.API.DTO;

public class PerfilDTO {
    private int id;
    private String tipo; // representando o Enum como String para facilitar transporte de dados

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
