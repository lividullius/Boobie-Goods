package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.PerfilDTO;
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
    
    // Lista simulada para desenvolvimento
    private List<Perfil> perfis = new ArrayList<>();
    private int proximoId = 1;
    
    // Construtor para inicializar dados de exemplo
    public PerfilController() {
        inicializarDadosDeTeste();
    }
    
    /**
     * GET /api/perfis - Listar todos os perfis
     */
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listarTodosPerfis() {
        List<PerfilDTO> perfisDTO = perfis.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(perfisDTO);
    }
    
    /**
     * GET /api/perfis/{id} - Buscar perfil por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> buscarPerfilPorId(@PathVariable int id) {
        Optional<Perfil> perfilOpt = perfis.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
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
            novoPerfil.setId(proximoId++);
            novoPerfil.setTipo(TipoPerfil.valueOf(perfilDTO.getTipo()));
            
            perfis.add(novoPerfil);
            
            PerfilDTO perfilCriado = converterParaDTO(novoPerfil);
            return ResponseEntity.ok(perfilCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
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
    public ResponseEntity<List<String>> listarPessoasComPerfil(@PathVariable int id) {
        Optional<Perfil> perfilOpt = perfis.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
        if (perfilOpt.isPresent()) {
            // TODO: Implementar busca real das pessoas com este perfil
            List<String> pessoas = new ArrayList<>();
            pessoas.add("João Silva");
            pessoas.add("Maria Santos");
            return ResponseEntity.ok(pessoas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/perfis/tipo/{tipo} - Buscar perfis por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PerfilDTO>> buscarPerfisPorTipo(@PathVariable String tipo) {
        try {
            TipoPerfil tipoPerfil = TipoPerfil.valueOf(tipo);
            List<PerfilDTO> perfisDoTipo = perfis.stream()
                    .filter(p -> p.getTipo() == tipoPerfil)
                    .map(this::converterParaDTO)
                    .toList();
            return ResponseEntity.ok(perfisDoTipo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Método auxiliar para converter Perfil para PerfilDTO
    private PerfilDTO converterParaDTO(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setTipo(perfil.getTipo().name());
        return dto;
    }
    
    // Inicializar dados de teste
    private void inicializarDadosDeTeste() {
        perfis.add(new Perfil(proximoId++, TipoPerfil.Developer));
        perfis.add(new Perfil(proximoId++, TipoPerfil.QualityAnalyst));
        perfis.add(new Perfil(proximoId++, TipoPerfil.Security));
        perfis.add(new Perfil(proximoId++, TipoPerfil.Gerente));
    }
}
