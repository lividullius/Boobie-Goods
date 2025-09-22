package com.boobiegoods.taskly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Alocacao {
    int idAlocacao;
    Projeto projeto;
    Contrato contrato;
    int horasSemanal;

    // Construtor
    public Alocacao(int idAlocacao, Projeto projeto, Contrato contrato, int horasSemanal) {
        this.idAlocacao = idAlocacao;
        this.projeto = projeto;
        this.contrato = contrato;
        this.horasSemanal = horasSemanal;
    }

    // Getters e setters
    public int getIdAlocacao() {
        return idAlocacao;
    }
    public void setIdAlocacao(int idAlocacao) {
        this.idAlocacao = idAlocacao;
    }
    public Projeto getProjeto() {
        return projeto;
    }
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    public int getHorasSemanal() {
        return horasSemanal;
    }
    public void setHorasSemanal(int horasSemanal) {
        this.horasSemanal = horasSemanal;
    }
}
