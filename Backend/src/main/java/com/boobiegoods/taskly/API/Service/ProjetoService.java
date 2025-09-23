package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.ProjetoRepository;
import com.boobiegoods.taskly.Domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {
    
    @Autowired
    private ProjetoRepository projetoRepository;
    
    /* Buscar todos os projetos */
    public List<Projeto> findAll() {
        return projetoRepository.findAll();
    }
    /* Buscar projeto por ID */
    public Optional<Projeto> findById(Integer id) {
        return projetoRepository.findById(id);
    }

    /* Salvar projeto (criar ou atualizar) */
    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    /* Deletar projeto por ID */
    public void deleteById(Integer id) {
        projetoRepository.deleteById(id);
    }
    
    /* Verificar se projeto existe */
    public boolean existsById(Integer id) {
        return projetoRepository.existsById(id);
    }

    /* Buscar projeto por nome */
    public Optional<Projeto> findByNome(String nome) {
        return projetoRepository.findByNomeProjeto(nome);
    }

    /* Buscar projetos por nome contendo texto */
    public List<Projeto> findByNomeContaining(String nome) {
        return projetoRepository.findByNomeProjetoContainingIgnoreCase(nome);
    }

    /* Buscar projetos por descrição contendo texto */
    public List<Projeto> findByDescricaoContaining(String descricao) {
        return projetoRepository.findByDescricaoProjetoContainingIgnoreCase(descricao);
    }

    /* Buscar projetos ativos */
    public List<Projeto> findProjetosAtivos() {
        return projetoRepository.findProjetosAtivos(LocalDate.now());
    }

    /* Buscar projetos finalizados */
    public List<Projeto> findProjetosFinalizados() {
        return projetoRepository.findProjetosFinalizados(LocalDate.now());
    }
    
    /* Buscar projetos por período */
    public List<Projeto> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return projetoRepository.findByDataInicioProjetoBetween(dataInicio, dataFim);
    }

    /* Contar projetos ativos */
    public long countProjetosAtivos() {
        return projetoRepository.countProjetosAtivos(LocalDate.now());
    }

    /* Buscar projetos com alocações */
    public List<Projeto> findProjetosComAlocacoes() {
        return projetoRepository.findProjetosComAlocacoes();
    }

    /* Buscar projetos sem alocações */
    public List<Projeto> findProjetosSemAlocacoes() {
        return projetoRepository.findProjetosSemAlocacoes();
    }

    /* Verificar se nome do projeto já existe */
    public boolean existsByNome(String nome) {
        return projetoRepository.existsByNomeProjetoIgnoreCase(nome);
    }

    /* Buscar projetos ordenados por data de início */
    public List<Projeto> findAllOrderByDataInicio() {
        return projetoRepository.findAllByOrderByDataInicioProjetoAsc();
    }

    /* Validar regras de negócio antes de salvar */
    public Projeto saveWithValidation(Projeto projeto) {
        // Validar se nome já existe (para novos projetos)
        if (projeto.getId() == 0 && existsByNome(projeto.getNomeProjeto())) {
            throw new IllegalArgumentException("Já existe um projeto com este nome");
        }
        
        // Validar datas
        if (projeto.getDataTerminoProjeto() != null && 
            projeto.getDataInicioProjeto().isAfter(projeto.getDataTerminoProjeto())) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de término");
        }
        
        return save(projeto);
    }
}
