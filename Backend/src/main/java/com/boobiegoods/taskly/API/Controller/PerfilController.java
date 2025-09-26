package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.PerfilDTO;
import com.boobiegoods.taskly.API.DTO.PessoaDTO;
import com.boobiegoods.taskly.API.Service.PerfilService;
import com.boobiegoods.taskly.API.Service.PessoaService;
import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfis")
public class PerfilController {

    private final PerfilService perfilService;
    private final PessoaService pessoaService;

    // Construtor para inicializar dados de exemplo
    public PerfilController(PerfilService perfilService, PessoaService pessoaService) {
        this.perfilService = perfilService;
        this.pessoaService = pessoaService;
    }

    /**
     * GET /api/perfis - Listar todos os perfis
     */
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listarTodosPerfis() {
        List<PerfilDTO> perfisDTO = perfilService.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(perfisDTO);
    }

    /**
     * GET /api/perfis/{id} - Buscar perfil por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> buscarPerfilPorId(@PathVariable int id) {
        Optional<Perfil> perfilOpt = perfilService.findById(id);

        if (perfilOpt.isPresent()) {
            PerfilDTO perfilDTO = converterParaDTO(perfilOpt.get());
            return ResponseEntity.ok(perfilDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/perfis - Criar novo perfil
     */
    @PostMapping
    public ResponseEntity<PerfilDTO> criarPerfil(@RequestBody PerfilDTO perfilDTO) {
        try {
            Perfil novoPerfil = new Perfil();
            novoPerfil.setTipo(TipoPerfil.valueOf(perfilDTO.getTipo()));

            Perfil salvo = perfilService.saveWithValidation(novoPerfil);
            return ResponseEntity.ok(converterParaDTO(salvo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * GET /api/perfis/tipos - Listar tipos de perfil disponíveis
     */
    @GetMapping("/tipos")
    public ResponseEntity<List<String>> listarTiposPerfil() {
        List<String> tipos = new ArrayList<>();
        for (TipoPerfil tipo : TipoPerfil.values()) {
            tipos.add(tipo.name());
        }
        return ResponseEntity.ok(tipos);
    }

    /**
     * GET /api/perfis/{id}/pessoas - Listar pessoas com este perfil
     */
    @GetMapping("/{id}/pessoas")
    public ResponseEntity<List<PessoaDTO>> listarPessoasComPerfil(@PathVariable int id) {
        List<PessoaDTO> pessoasDTO = pessoaService.findByPerfilId(id)
                .stream()
                .map(p -> new PessoaDTO(p.getId(), p.getNome()))
                .toList();

        if (pessoasDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pessoasDTO);
    }

    /**
     * GET /api/perfis/tipo/{tipo} - Buscar perfis por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<PerfilDTO> buscarPerfilPorTipo(@PathVariable String tipo) {
    try {

        TipoPerfil tipoPerfil = TipoPerfil.valueOf(tipo.toUpperCase());
        Optional<Perfil> perfilOpt = perfilService.findByTipo(tipoPerfil);

        return perfilOpt
                .map(perfil -> ResponseEntity.ok(new PerfilDTO(perfil.getId(), perfil.getTipo().toString())))
                .orElseGet(() -> ResponseEntity.notFound().build());

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}

    // Método auxiliar para converter Perfil para PerfilDTO
    public PerfilDTO converterParaDTO(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO(perfil.getId(), perfil.getTipo().name());
        return dto;
    }
}
