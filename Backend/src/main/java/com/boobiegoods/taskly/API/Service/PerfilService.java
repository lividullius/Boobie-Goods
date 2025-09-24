package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.PerfilRepository;
import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {
    
    @Autowired
    private PerfilRepository perfilRepository;
    
    //Buscar todos os perfis
    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }
    
    //Buscar perfil por ID
    public Optional<Perfil> findById(Integer id) {
        return perfilRepository.findById(id);
    }
    
    //Salvar perfil (criar ou atualizar)
    public Perfil save(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    //Deletar perfil por ID
    public void deleteById(Integer id) {
        perfilRepository.deleteById(id);
    }
    //Verificar se perfil existe
    public boolean existsById(Integer id) {
        return perfilRepository.existsById(id);
    }

    //Buscar perfis por tipo
    public Optional<Perfil> findByTipo(TipoPerfil tipo) {
        return perfilRepository.findByTipo(tipo);
    }

    //Buscar perfis por tipos
    public List<Perfil> findByTipos(List<TipoPerfil> tipos) {
        return perfilRepository.findByTipoIn(tipos);
    }

    //Verificar se perfil existe por tipo
    public boolean existsByTipo(TipoPerfil tipo) {
        return perfilRepository.existsByTipo(tipo);
    }

    //Buscar perfis com pessoas associadas
    public List<Perfil> findPerfisComPessoas() {
        return perfilRepository.findPerfisComPessoas();
    }

    //Buscar perfis sem pessoas associadas
    public List<Perfil> findPerfisSemPessoas() {
        return perfilRepository.findPerfisSemPessoas();
    }

    //Buscar perfis com contratos
    public List<Perfil> findPerfisComContratos() {
        return perfilRepository.findPerfisComContratos();
    }

    //Contar perfis por tipo
    public long countByTipo(TipoPerfil tipo) {
        return perfilRepository.countByTipo(tipo);
    }

    //Buscar todos os perfis ordenados por tipo
    public List<Perfil> findAllOrderByTipo() {
        return perfilRepository.findAllByOrderByTipoAsc();
    }

    //Buscar perfis de uma pessoa
    public List<Perfil> findByPessoaId(Integer pessoaId) {
        return perfilRepository.findByPessoaId(pessoaId);
    }

    //Salvar com validação de regras de negócio
    public Perfil saveWithValidation(Perfil perfil) {
        // Validar se tipo já existe (para novos perfis)
        if (perfil.getId() == 0 && existsByTipo(perfil.getTipo())) {
            throw new IllegalArgumentException("Já existe um perfil com este tipo");
        }
        
        // Validar tipo não nulo
        if (perfil.getTipo() == null) {
            throw new IllegalArgumentException("Tipo do perfil é obrigatório");
        }
        
        return save(perfil);
    }
}
