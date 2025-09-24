package com.boobiegoods.taskly.Domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Projeto")
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDProjeto")
    private int id;
    
    @Column(name = "Nome", nullable = false, length = 100)
    private String nomeProjeto;
    
    @Column(name = "Descricao", length = 255)
    private String descricaoProjeto;
    
    @Column(name = "DataInicioProj", nullable = false)
    private LocalDate dataInicioProjeto;
    
    @Column(name = "DataFimProj")
    private LocalDate dataTerminoProjeto;
    
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacoes = new HashSet<>();
    
    // Construtor padrão (obrigatório para JPA)
    public Projeto() {}
    
    // Construtor
    public Projeto(int idProjeto, String nomeProjeto, String descricaoProjeto, LocalDate dataInicioProjeto, LocalDate dataTerminoProjeto) {
        this.id = idProjeto;
        this.nomeProjeto = nomeProjeto;
        this.descricaoProjeto = descricaoProjeto;
        this.dataInicioProjeto = dataInicioProjeto;
        this.dataTerminoProjeto = dataTerminoProjeto;
    }  

    // Getters e setters 
    public int getId() {
        return id;
    }
    public void setId(int idProjeto) {
        this.id = idProjeto;
    }
    public String getNomeProjeto() {
        return nomeProjeto;
    }
    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }
    public String getDescricaoProjeto() {
        return descricaoProjeto;
    }
    public void setDescricaoProjeto(String descricaoProjeto) {
        this.descricaoProjeto = descricaoProjeto;
    }
    public LocalDate getDataInicioProjeto() {
        return dataInicioProjeto;
    }
    public void setDataInicioProjeto(LocalDate dataInicioProjeto) {
        this.dataInicioProjeto = dataInicioProjeto;
    }
    public LocalDate getDataTerminoProjeto() {
        return dataTerminoProjeto;
    }
    public void setDataTerminoProjeto(LocalDate dataTerminoProjeto) {
        this.dataTerminoProjeto = dataTerminoProjeto;
    }
    public Set<Alocacao> getAlocacoes() {
        return alocacoes;
    }
    public void setAlocacoes(Set<Alocacao> alocacoes) {
        this.alocacoes = alocacoes;
    }
}
