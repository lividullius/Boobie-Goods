package com.boobiegoods.taskly.API.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boobiegoods.taskly.Data.Interfaces.IPessoaPerfilRepository;
import com.boobiegoods.taskly.Data.Repository.PessoaPerfilRepository;
import com.boobiegoods.taskly.Domain.Perfil;

@Service
public class PessoaPerfilService {

    private final IPessoaPerfilRepository pessoaPerfilRepository;

    public PessoaPerfilService(IPessoaPerfilRepository pessoaPerfilRepository) {
        this.pessoaPerfilRepository = pessoaPerfilRepository;
    }

    public void associarPerfis(int pessoaId, List<Integer> idsPerfis) {
        for (Integer perfilId : idsPerfis) {
            pessoaPerfilRepository.associarPerfil(pessoaId, perfilId);
        }
    }

    public List<Perfil> listarPerfisPessoa(int pessoaId) {
        return pessoaPerfilRepository.listarPerfisDaPessoa(pessoaId);
    }
}

