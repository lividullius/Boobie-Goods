package com.boobiegoods.taskly.API.DTO;

public class PessoaDTO {
    private int id;
    private String nome;

    // Construtor padrão
    public PessoaDTO() {}

    // Construtor cheio
    public PessoaDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}