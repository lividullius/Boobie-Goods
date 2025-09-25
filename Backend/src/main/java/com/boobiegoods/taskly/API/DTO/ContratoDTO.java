package com.boobiegoods.taskly.API.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContratoDTO {
    private int id;
    private int fkPessoa;
    private int fkPerfil;
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private int numeroHorasSemana;
    private BigDecimal salarioHora;  

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFkPessoa() {
        return fkPessoa;
    }
    public void setFkPessoa(int fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public int getFkPerfil() {
        return fkPerfil;
    }
    public void setFkPerfil(int fkPerfil) {
        this.fkPerfil = fkPerfil;
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

    public BigDecimal getSalarioHora() { return salarioHora; }
    public void setSalarioHora(BigDecimal valorHora) { this.salarioHora = valorHora; }

    }



