package com.boobiegoods.taskly;

import java.time.LocalDate;

public class Contrato {
    private int id;
    private Pessoa pessoa;
    private Perfil perfil;
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private int numeroHorasSemana;
    private double valorHora;

    // Construtor
    public Contrato(int idContrato, Pessoa pessoa, Perfil perfil, LocalDate dataInicio, LocalDate dataFimContrato, int numeroHorasSemana, double valorHora) {
        this.id = idContrato;
        this.pessoa = pessoa;
        this.perfil = perfil;
        this.dataInicioContrato = dataInicio;
        this.dataFimContrato = dataFimContrato;
        this.numeroHorasSemana = numeroHorasSemana;
        this.valorHora = valorHora;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int idContrato) {
        this.id = idContrato;
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
    public LocalDate getDataInicioContrato() {
        return dataInicioContrato;
    }
    public void setDataInicioContrato(LocalDate dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }
    public LocalDate getDataFimContrato() {
        return dataFimContrato;
    }
    public void setDataFimContrato(LocalDate dataFimContrato) {
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
