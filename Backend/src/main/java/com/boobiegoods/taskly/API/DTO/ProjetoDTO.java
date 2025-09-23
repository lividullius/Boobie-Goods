package com.boobiegoods.taskly.API.DTO;

import java.time.LocalDate;

public class ProjetoDTO {
    private int id;
    private String nomeProjeto;
    private String descricaoProjeto;
    private LocalDate dataInicioProjeto;
    private LocalDate dataTerminoProjeto;

    // Construtor padrão
    public ProjetoDTO() {}

    // Construtor cheio
    public ProjetoDTO(int id, String nomeProjeto, String descricaoProjeto, 
                      LocalDate dataInicioProjeto, LocalDate dataTerminoProjeto) {
        this.id = id;
        this.nomeProjeto = nomeProjeto;
        this.descricaoProjeto = descricaoProjeto;
        this.dataInicioProjeto = dataInicioProjeto;
        this.dataTerminoProjeto = dataTerminoProjeto;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
