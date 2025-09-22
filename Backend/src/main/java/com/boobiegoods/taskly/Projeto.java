package com.boobiegoods.taskly;

public class Projeto {
    int idProjeto;
    String nomeProjeto;
    String descricaoProjeto;
    String dataInicioProjeto;
    String dataTerminoProjeto;
    
    // Construtor
    public Projeto(int idProjeto, String nomeProjeto, String descricaoProjeto, String dataInicioProjeto, String dataTerminoProjeto) {
        this.idProjeto = idProjeto;
        this.nomeProjeto = nomeProjeto;
        this.descricaoProjeto = descricaoProjeto;
        this.dataInicioProjeto = dataInicioProjeto;
        this.dataTerminoProjeto = dataTerminoProjeto;
    }  

    // Getters e setters 
    public int getIdProjeto() {
        return idProjeto;
    }
    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
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
    public String getDataInicioProjeto() {
        return dataInicioProjeto;
    }
    public void setDataInicioProjeto(String dataInicioProjeto) {
        this.dataInicioProjeto = dataInicioProjeto;
    }
    public String getDataTerminoProjeto() {
        return dataTerminoProjeto;
    }
    public void setDataTerminoProjeto(String dataTerminoProjeto) {
        this.dataTerminoProjeto = dataTerminoProjeto;
    }
}
