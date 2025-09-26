package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.PerfilRepository;
import com.boobiegoods.taskly.Data.Repository.PessoaRepository;
import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    
    private final PessoaRepository pessoaRepository;
    private final PerfilService perfilService;
    
    public PessoaService(PessoaRepository pessoaRepository, PerfilService perfilService){
        this.pessoaRepository = pessoaRepository;
        this.perfilService = perfilService;
    }

    // Buscar todas as pessoas
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }
    
    // Buscar pessoa por ID
    public Optional<Pessoa> findById(Integer id) {
        return pessoaRepository.findById(id);
    }
    
    // Salvar pessoa (criar ou atualizar)
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
    
    // Deletar pessoa por ID
    public void deleteById(Integer id) {
        pessoaRepository.deleteById(id);
    }
    
    // Verificar se pessoa existe
    public boolean existsById(Integer id) {
        return pessoaRepository.existsById(id);
    }
    
    // Buscar pessoa por nome
    public Optional<Pessoa> findByNome(String nome) {
        return pessoaRepository.findByNome(nome);
    }
    
    // Buscar pessoas por nome contendo texto
    public List<Pessoa> findByNomeContaining(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    // Buscar pessoas por perfil
    public List<Pessoa> findByPerfilId(Integer perfilId) {
        return pessoaRepository.findByPerfilId(perfilId);
    }

    public void associarPerfis(int id, List<Integer> idsPerfis) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        for (Integer perfilId : idsPerfis) {
            pessoaRepository.associarPerfil(id, perfilId);
        }
    }
    
    // Buscar pessoas com contratos
    public List<Pessoa> findPessoasComContratos() {
        return pessoaRepository.findPessoasComContratos();
    }
    
    // Buscar pessoas sem contratos
    public List<Pessoa> findPessoasSemContratos() {
        return pessoaRepository.findPessoasSemContratos();
    }
    
    // Buscar pessoas com alocações
    public List<Pessoa> findPessoasComAlocacoes() {
        return pessoaRepository.findPessoasComAlocacoes();
    }
    
    // Buscar pessoas alocadas em um projeto
    public List<Pessoa> findByProjetoId(Integer projetoId) {
        return pessoaRepository.findByProjetoId(projetoId);
    }
    
    // Contar pessoas com contratos ativos
    public long countPessoasComContratosAtivos() {
        return pessoaRepository.countPessoasComContratosAtivos();
    }
    
    // Buscar todas as pessoas ordenadas por nome
    public List<Pessoa> findAllOrderByNome() {
        return pessoaRepository.findAllByOrderByNomeAsc();
    }
    
    // Verificar se nome já existe
    public boolean existsByNome(String nome) {
        return pessoaRepository.existsByNomeIgnoreCase(nome);
    }
    
    // Salvar com validação de regras de negócio
    public Pessoa saveWithValidation(Pessoa pessoa) {
        // Validar se nome já existe (para novas pessoas)
        if (pessoa.getId() == 0 && existsByNome(pessoa.getNome())) {
            throw new IllegalArgumentException("Já existe uma pessoa com este nome");
        }
        
        // Validar nome não vazio
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da pessoa é obrigatório");
        }
        
        return save(pessoa);
    }
}
