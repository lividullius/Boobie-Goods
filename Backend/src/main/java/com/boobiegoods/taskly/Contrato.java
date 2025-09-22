package com.boobiegoods.taskly;

public class Contrato {
    int idContrato;
    Pessoa pessoa;
    Perfil perfil;
    String dataInicioContrato;
    String dataFimContrato;
    int numeroHorasSemana;
    double valorHora;

    // Construtor
    public Contrato(int idContrato, Pessoa pessoa, Perfil perfil, String dataInicio, String dataFimContrato, int numeroHorasSemana, double valorHora) {
        this.idContrato = idContrato;
        this.pessoa = pessoa;
        this.perfil = perfil;
        this.dataInicioContrato = dataInicio;
        this.dataFimContrato = dataFimContrato;
        this.numeroHorasSemana = numeroHorasSemana;
        this.valorHora = valorHora;
    }

    // Getters e setters
    public int getIdContrato() {
        return idContrato;
    }
    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public Perfil getPerfil() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    public String getDataInicioContrato() {
        return dataInicioContrato;
    }
    public void setDataInicioContrato(String dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }
    public String getDataFimContrato() {
        return dataFimContrato;
    }
    public void setDataFimContrato(String dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }
    public int getNumeroHorasSemana() {
        return numeroHorasSemana;
    }
    public void setNumeroHorasSemana(int numeroHorasSemana) {
        this.numeroHorasSemana = numeroHorasSemana;
    }
    public double getValorHora() {
        return valorHora;
    }
    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

}
