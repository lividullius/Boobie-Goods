package com.boobiegoods.taskly;

public class Pessoa {
    private int id;
    private String nome;
    

    // Construtor
    public Pessoa(int idPessoa, String nome) {
        this.id = idPessoa;
        this.nome = nome;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int idPessoa) {
        this.id = idPessoa;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
