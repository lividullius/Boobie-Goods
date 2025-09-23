package com.boobiegoods.taskly;

import java.time.LocalDate;

public class Projeto {
    private int id;
    private String nomeProjeto;
    private String descricaoProjeto;
    private LocalDate dataInicioProjeto;
    private LocalDate dataTerminoProjeto;
    
    // Construtor
    public Projeto(int idProjeto, String nomeProjeto, String descricaoProjeto, LocalDate dataInicioProjeto, LocalDate dataTerminoProjeto) {
        this.id = idProjeto;
        this.nomeProjeto = nomeProjeto;
        this.descricaoProjeto = descricaoProjeto;
        this.dataInicioProjeto = dataInicioProjeto;
        this.dataTerminoProjeto = dataTerminoProjeto;
    }  

    // Getters e setters 
    public int getId() {
        return id;
    }
    public void setId(int idProjeto) {
        this.id = idProjeto;
    }
    public String getNomeProjeto() {
        return nomeProjeto;
    }
    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }
    public String getDescricaoProjeto() {
        return descricaoProjeto;
    }
    public void setDescricaoProjeto(String descricaoProjeto) {
        this.descricaoProjeto = descricaoProjeto;
    }
    public LocalDate getDataInicioProjeto() {
        return dataInicioProjeto;
    }
    public void setDataInicioProjeto(LocalDate dataInicioProjeto) {
        this.dataInicioProjeto = dataInicioProjeto;
    }
    public LocalDate getDataTerminoProjeto() {
        return dataTerminoProjeto;
    }
    public void setDataTerminoProjeto(LocalDate dataTerminoProjeto) {
        this.dataTerminoProjeto = dataTerminoProjeto;
    }
}
