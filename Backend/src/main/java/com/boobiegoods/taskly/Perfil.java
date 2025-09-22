package com.boobiegoods.taskly;

public class Perfil {
    int idPerfil;
    TipoPerfil tipo;

    // Construtor
    public Perfil(int idPerfil, TipoPerfil tipo) {
        this.idPerfil = idPerfil;
        this.tipo = tipo;
    }
    
    // Getters e setters
    public int getIdPerfil() {
        return idPerfil;
    }
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    public TipoPerfil getTipo() {
        return tipo;
    }
    public void setTipo(TipoPerfil tipo) {
        this.tipo = tipo;
    }
}
