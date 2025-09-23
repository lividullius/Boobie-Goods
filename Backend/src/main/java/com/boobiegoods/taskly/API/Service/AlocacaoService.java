package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.AlocacaoRepository;
import com.boobiegoods.taskly.Domain.Alocacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlocacaoService {
    
    @Autowired
    private AlocacaoRepository alocacaoRepository;
    
    /**
     * Buscar todas as alocações
     */
    public List<Alocacao> findAll() {
        return alocacaoRepository.findAll();
    }
    
    /**
     * Buscar alocação por ID
     */
    public Optional<Alocacao> findById(Integer id) {
        return alocacaoRepository.findById(id);
    }
    
    /**
     * Salvar alocação (criar ou atualizar)
     */
    public Alocacao save(Alocacao alocacao) {
        return alocacaoRepository.save(alocacao);
    }
    
    /**
     * Deletar alocação por ID
     */
    public void deleteById(Integer id) {
        alocacaoRepository.deleteById(id);
    }
    
    /**
     * Verificar se alocação existe
     */
    public boolean existsById(Integer id) {
        return alocacaoRepository.existsById(id);
    }
    
    /**
     * Buscar alocações por projeto
     */
    public List<Alocacao> findByProjetoId(Integer projetoId) {
        return alocacaoRepository.findByProjetoId(projetoId);
    }
    
    /**
     * Buscar alocações por pessoa
     */
    public List<Alocacao> findByPessoaId(Integer pessoaId) {
        return alocacaoRepository.findByPessoaId(pessoaId);
    }
    
    /**
     * Buscar alocações por contrato
     */
    public List<Alocacao> findByContratoId(Integer contratoId) {
        return alocacaoRepository.findByContratoId(contratoId);
    }
    
    /**
     * Buscar alocação específica por projeto e pessoa
     */
    public Optional<Alocacao> findByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId) {
        return alocacaoRepository.findByProjetoIdAndPessoaId(projetoId, pessoaId);
    }
    
    /**
     * Buscar alocações por horas semanais
     */
    public List<Alocacao> findByHorasSemanal(Integer horas) {
        return alocacaoRepository.findByHorasSemanal(horas);
    }
    
    /**
     * Buscar alocações por intervalo de horas semanais
     */
    public List<Alocacao> findByHorasSemanal(Integer horasMin, Integer horasMax) {
        return alocacaoRepository.findByHorasSemanalBetween(horasMin, horasMax);
    }
    
    /**
     * Verificar se pessoa já está alocada em um projeto
     */
    public boolean existsByProjetoIdAndPessoaId(Integer projetoId, Integer pessoaId) {
        return alocacaoRepository.existsByProjetoIdAndPessoaId(projetoId, pessoaId);
    }
    
    /**
     * Calcular total de horas semanais de um projeto
     */
    public Integer calcularHorasTotaisProjeto(Integer projetoId) {
        return alocacaoRepository.sumHorasSemanalByProjetoId(projetoId);
    }
    
    /**
     * Calcular total de horas semanais de uma pessoa
     */
    public Integer calcularHorasTotaisPessoa(Integer pessoaId) {
        return alocacaoRepository.sumHorasSemanalByPessoaId(pessoaId);
    }
    
    /**
     * Contar alocações por projeto
     */
    public long countByProjetoId(Integer projetoId) {
        return alocacaoRepository.countByProjetoId(projetoId);
    }
    
    /**
     * Contar alocações por pessoa
     */
    public long countByPessoaId(Integer pessoaId) {
        return alocacaoRepository.countByPessoaId(pessoaId);
    }
    
    /**
     * Buscar alocações ordenadas por horas semanais (decrescente)
     */
    public List<Alocacao> findAllOrderByHorasSemanalDesc() {
        return alocacaoRepository.findAllByOrderByHorasSemanalDesc();
    }
    
    /**
     * Buscar alocações de pessoas que excedem determinado número de horas
     */
    public List<Alocacao> findAlocacoesPessoasComExcessoHoras(Integer maxHoras) {
        return alocacaoRepository.findAlocacoesPessoasComExcessoHoras(maxHoras);
    }
    
    /**
     * Buscar alocações de projetos que excedem determinado número de horas
     */
    public List<Alocacao> findAlocacoesProjetosComExcessoHoras(Integer maxHoras) {
        return alocacaoRepository.findAlocacoesProjetosComExcessoHoras(maxHoras);
    }
    
    /**
     * Buscar alocações de um projeto por contrato
     */
    public List<Alocacao> findByProjetoIdAndContratoId(Integer projetoId, Integer contratoId) {
        return alocacaoRepository.findByProjetoIdAndContratoId(projetoId, contratoId);
    }
    
    /**
     * Validar se pessoa não excede 40h semanais
     */
    public boolean validarLimiteHoras(Integer pessoaId) {
        Integer horasTotais = calcularHorasTotaisPessoa(pessoaId);
        return horasTotais <= 40;
    }
    
    /**
     * Salvar com validação de regras de negócio
     */
    public Alocacao saveWithValidation(Alocacao alocacao) {
        // Validar se pessoa já está alocada no projeto
        if (alocacao.getIdAlocacao() == 0 && 
            existsByProjetoIdAndPessoaId(alocacao.getProjeto().getId(), alocacao.getPessoa().getId())) {
            throw new IllegalArgumentException("Pessoa já está alocada neste projeto");
        }
        
        // Validar horas semanais
        if (alocacao.getHorasSemanal() <= 0 || alocacao.getHorasSemanal() > 40) {
            throw new IllegalArgumentException("Horas semanais devem estar entre 1 e 40");
        }
        
        // Validar se não excede 40h total (apenas para novas alocações)
        if (alocacao.getIdAlocacao() == 0) {
            Integer horasAtuais = calcularHorasTotaisPessoa(alocacao.getPessoa().getId());
            if (horasAtuais + alocacao.getHorasSemanal() > 40) {
                throw new IllegalArgumentException("Alocação excederia o limite de 40 horas semanais da pessoa");
            }
        }
        
        // Validar entidades obrigatórias
        if (alocacao.getProjeto() == null) {
            throw new IllegalArgumentException("Projeto é obrigatório");
        }
        
        if (alocacao.getPessoa() == null) {
            throw new IllegalArgumentException("Pessoa é obrigatória");
        }
        
        if (alocacao.getContrato() == null) {
            throw new IllegalArgumentException("Contrato é obrigatório");
        }
        
        return save(alocacao);
    }
}
