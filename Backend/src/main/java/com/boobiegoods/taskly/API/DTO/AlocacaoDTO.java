package com.boobiegoods.taskly.API.DTO;

public class AlocacaoDTO {
    private int idAlocacao;
    private int idProjeto;
    private int idContrato;
    private int idPessoa;
    private int horasSemanal;

    // Getters e setters
    public int getIdAlocacao() {
        return idAlocacao;
    }

    public void setIdAlocacao(int idAlocacao) {
        this.idAlocacao = idAlocacao;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getHorasSemanal() {
        return horasSemanal;
    }

    public void setHorasSemanal(int horasSemanal) {
        this.horasSemanal = horasSemanal;
    }

}