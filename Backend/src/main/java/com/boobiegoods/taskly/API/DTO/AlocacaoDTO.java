package com.boobiegoods.taskly.API.DTO;

public class AlocacaoDTO {
    private int id;
    private int fkProjeto;
    private int fkContrato;
    private int fkPessoa;
    private int horasSemanal;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFkProjeto() {
        return fkProjeto;
    }
    public void setFkProjeto(int fkProjeto) {
        this.fkProjeto = fkProjeto;
    }

    public int getFkContrato() {
        return fkContrato;
    }
    public void setFkContrato(int fkContrato) {
        this.fkContrato = fkContrato;
    }

    public int getFkPessoa() {
        return fkPessoa;
    }
    public void setFkPessoa(int fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public int getHorasSemanal() {
        return horasSemanal;
    }
    public void setHorasSemanal(int horasSemanal) {
        this.horasSemanal = horasSemanal;
    }
}
