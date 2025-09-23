package com.boobiegoods.taskly;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDContrato")
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPessoa", nullable = false)
    private Pessoa pessoa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPerfil", nullable = false)
    private Perfil perfil;
    
    @Column(name = "DataInicioContrato", nullable = false)
    private LocalDate dataInicioContrato;
    
    @Column(name = "DataFimContrato", nullable = false)
    private LocalDate dataFimContrato;
    
    @Column(name = "NumHoraSemanal", nullable = false)
    private int numeroHorasSemana;
    
    @Column(name = "SalarioHora", nullable = false, precision = 10, scale = 2)
    private double valorHora;
    
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacoes = new HashSet<>();

    // Construtor padrão (obrigatório para JPA)
    public Contrato() {}

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
    public Set<Alocacao> getAlocacoes() {
        return alocacoes;
    }
    public void setAlocacoes(Set<Alocacao> alocacoes) {
        this.alocacoes = alocacoes;
    }

}
