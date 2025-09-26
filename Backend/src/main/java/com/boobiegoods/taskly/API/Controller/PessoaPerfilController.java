package com.boobiegoods.taskly.API.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boobiegoods.taskly.API.DTO.PerfilDTO;
import com.boobiegoods.taskly.API.Service.PerfilService;
import com.boobiegoods.taskly.API.Service.PessoaPerfilService;
import com.boobiegoods.taskly.Domain.Perfil;

@RestController
@RequestMapping("/api/pessoas-perfis")
public class PessoaPerfilController {

    private final PessoaPerfilService pessoaPerfilService;
    private final PerfilController perfilController;

    public PessoaPerfilController(PessoaPerfilService pessoaPerfilService, PerfilController perfilController) {
        this.pessoaPerfilService = pessoaPerfilService;
        this.perfilController = perfilController;
    }

    // Associa perfis a uma pessoa
    @PostMapping("/{pessoaId}")
    public ResponseEntity<Void> associarPerfis(
            @PathVariable int pessoaId,
            @RequestBody List<Integer> idsPerfis) {
        pessoaPerfilService.associarPerfis(pessoaId, idsPerfis);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<List<PerfilDTO>> listarPerfis(@PathVariable int pessoaId) {
        List<Perfil> perfis = pessoaPerfilService.listarPerfisPessoa(pessoaId);

        List<PerfilDTO> perfisDTO = new ArrayList<PerfilDTO>();
            
        for (Perfil p : perfis) {
            perfisDTO.add(perfilController.converterParaDTO(p));
        }

        return ResponseEntity.ok(perfisDTO);
    }
}

