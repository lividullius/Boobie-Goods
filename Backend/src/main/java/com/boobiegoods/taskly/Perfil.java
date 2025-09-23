package com.boobiegoods.taskly;

public class Perfil {
    private int id;
    private TipoPerfil tipo;

    // Construtor
    public Perfil(int idPerfil, TipoPerfil tipo) {
        this.id = idPerfil;
        this.tipo = tipo;
    }
    
    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int idPerfil) {
        this.id = idPerfil;
    }
    public TipoPerfil getTipo() {
        return tipo;
    }
    public void setTipo(TipoPerfil tipo) {
        this.tipo = tipo;
    }
}
