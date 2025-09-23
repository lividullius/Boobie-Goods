package com.boobiegoods.taskly;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPessoa")
    private int id;
    
    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "PessoaPerfil",
        joinColumns = @JoinColumn(name = "IDPessoa"),
        inverseJoinColumns = @JoinColumn(name = "IDPerfil")
    )
    private Set<Perfil> perfis = new HashSet<>();
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contrato> contratos = new HashSet<>();
    
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Alocacao> alocacoes = new HashSet<>();

    // Construtor padrão (obrigatório para JPA)
    public Pessoa() {}

    // Construtor
    public Pessoa(int idPessoa, String nome) {
        this.id = idPessoa;
        this.nome = nome;
    }

    // Getters e setters
    public int getId() {
        return id;
    }
    public void setId(int idPessoa) {
        this.id = idPessoa;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Set<Perfil> getPerfis() {
        return perfis;
    }
    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }
    public Set<Contrato> getContratos() {
        return contratos;
    }
    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }
    public Set<Alocacao> getAlocacoes() {
        return alocacoes;
    }
    public void setAlocacoes(Set<Alocacao> alocacoes) {
        this.alocacoes = alocacoes;
    }
}
