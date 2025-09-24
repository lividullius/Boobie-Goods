package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.ProjetoDTO;
import com.boobiegoods.taskly.API.Service.ProjetoService;
import com.boobiegoods.taskly.Domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin(origins = "*")
public class ProjetoController {
    
    private final ProjetoService projetoService;

    public ProjetoController (ProjetoService projetoService){
        this.projetoService = projetoService;
    }
    
    /**
     * GET /api/projetos - Listar todos os projetos
     */
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
        List<Projeto> projetos = projetoService.findAll();
        List<ProjetoDTO> projetosDTO = projetos.stream()
                .map(this::converterParaDTO)
                .toList();
        
        return ResponseEntity.ok(projetosDTO);
    }
    
    /**
     * GET /api/projetos/{id} - Buscar projeto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> buscarProjeto(@PathVariable int id) {
        Optional<Projeto> projetoOpt = projetoService.findById(id);
        
        if (projetoOpt.isPresent()) {
            ProjetoDTO dto = converterParaDTO(projetoOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST /api/projetos - Criar novo projeto
     */
    @PostMapping
    public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        try {
            // Criar nova entity a partir do DTO
            Projeto novoProjeto = new Projeto();
            novoProjeto.setNomeProjeto(projetoDTO.getNomeProjeto());
            novoProjeto.setDescricaoProjeto(projetoDTO.getDescricaoProjeto());
            novoProjeto.setDataInicioProjeto(projetoDTO.getDataInicioProjeto());
            novoProjeto.setDataTerminoProjeto(projetoDTO.getDataTerminoProjeto());
            
            Projeto projetoSalvo = projetoService.saveWithValidation(novoProjeto);
            
            ProjetoDTO responseDTO = converterParaDTO(projetoSalvo);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
        
    /**
     * Converter Entity para DTO
     */
    private ProjetoDTO converterParaDTO(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setNomeProjeto(projeto.getNomeProjeto());
        dto.setDescricaoProjeto(projeto.getDescricaoProjeto());
        dto.setDataInicioProjeto(projeto.getDataInicioProjeto());
        dto.setDataTerminoProjeto(projeto.getDataTerminoProjeto());
        return dto;
    }
    
    /**
     * GET /api/projetos/{id}/custo - Calcular custo geral do projeto
     */
    @GetMapping("/{id}/custo")
    public ResponseEntity<Double> calcularCustoProjeto(@PathVariable int id) {
        Optional<Projeto> projetoOpt = projetoService.findById(id);
        
        if (projetoOpt.isPresent()) {
            Projeto projeto = projetoOpt.get();
            
            // TODO: Implementar cálculo real baseado nas alocações
            // Por enquanto retorna valor simulado
            double custoTotal = 50000.0; // Custo total simulado
            
            return ResponseEntity.ok(custoTotal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/projetos/{id}/custo-periodo?dataInicio=yyyy-MM-dd&dataFim=yyyy-MM-dd
     * Calcular custo por período específico
     */
    @GetMapping("/{id}/custo-periodo")
    public ResponseEntity<Double> calcularCustoPorPeriodo(
            @PathVariable int id,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        
        Optional<Projeto> projetoOpt = projetoService.findById(id);
        
        if (projetoOpt.isPresent()) {
            // TODO: Implementar cálculo real baseado no período e alocações
            // Por enquanto retorna valor simulado
            double custoPeriodo = 15000.0; // Valor simulado
            return ResponseEntity.ok(custoPeriodo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/projetos/ativos - Listar projetos ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<ProjetoDTO>> listarProjetosAtivos() {
        List<Projeto> projetos = projetoService.findProjetosAtivos();
        List<ProjetoDTO> projetosDTO = projetos.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(projetosDTO);
    }
    
    /**
     * GET /api/projetos/buscar - Buscar projetos por nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProjetoDTO>> buscarProjetosPorNome(@RequestParam String nome) {
        List<Projeto> projetos = projetoService.findByNomeContaining(nome);
        List<ProjetoDTO> projetosDTO = projetos.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(projetosDTO);
    }
}
