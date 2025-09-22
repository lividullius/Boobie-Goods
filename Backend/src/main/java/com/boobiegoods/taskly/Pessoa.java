package com.boobiegoods.taskly;

public class Pessoa {
    int idPessoa;
    String nome;

    // Construtor
    public Pessoa(int idPessoa, String nome) {
        this.idPessoa = idPessoa;
        this.nome = nome;
    }

    // Getters e setters
    public int getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
