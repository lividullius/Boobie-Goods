package com.boobiegoods.taskly.Domain;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPerfil")
    private int id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo", nullable = false, length = 20)
    private TipoPerfil tipo;
    
    @ManyToMany(mappedBy = "perfis", fetch = FetchType.LAZY)
    private Set<Pessoa> pessoas = new HashSet<>();
    
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contrato> contratos = new HashSet<>();

    // Construtor padrão (obrigatório para JPA)
    public Perfil() {}

    // Construtor
    public Perfil(int idPerfil, TipoPerfil tipo) {
        this.id = idPerfil;
        this.tipo = tipo;
    }
    
    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int idPerfil) {
        this.id = idPerfil;
    }
    public TipoPerfil getTipo() {
        return tipo;
    }
    public void setTipo(TipoPerfil tipo) {
        this.tipo = tipo;
    }
    public Set<Pessoa> getPessoas() {
        return pessoas;
    }
    public void setPessoas(Set<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    public Set<Contrato> getContratos() {
        return contratos;
    }
    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }
}
