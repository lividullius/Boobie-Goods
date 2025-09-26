package com.boobiegoods.taskly.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Alocacao", uniqueConstraints = {
    @UniqueConstraint(name = "uq_pessoa_projeto", columnNames = {"IDProjeto", "IDContrato"})
})
public class Alocacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAlocacao")
    private int idAlocacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProjeto", nullable = false)
    private Projeto projeto;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "IDContrato", nullable = false)
    private Contrato contrato;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPessoa", nullable = true)
    private Pessoa pessoa;
    
    @Column(name = "horaSemana", nullable = false)
    private int horasSemanal;

    // Construtor padrão (obrigatório para JPA)
    public Alocacao() {}

    // Construtor
    public Alocacao(int idAlocacao, Projeto projeto, Contrato contrato, Pessoa pessoa, int horasSemanal) {
        this.idAlocacao = idAlocacao;
        this.projeto = projeto;
        this.contrato = contrato;
        this.pessoa = pessoa;
        this.horasSemanal = horasSemanal;
    }

    // Getters e setters
    public int getIdAlocacao() {
        return idAlocacao;
    }
    public void setIdAlocacao(int idAlocacao) {
        this.idAlocacao = idAlocacao;
    }
    public Projeto getProjeto() {
        return projeto;
    }
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public int getHorasSemanal() {
        return horasSemanal;
    }
    public void setHorasSemanal(int horasSemanal) {
        this.horasSemanal = horasSemanal;
    }
}
