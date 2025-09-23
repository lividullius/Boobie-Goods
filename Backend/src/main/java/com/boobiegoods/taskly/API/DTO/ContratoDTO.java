package com.boobiegoods.taskly.API.DTO;

import java.time.LocalDate;

public class ContratoDTO {
    private int id;
    private int idPessoa;
    private int idPerfil;
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private int numeroHorasSemana;
    private double valorHora;

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getIdPerfil() {
        return idPerfil;
    }
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
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
