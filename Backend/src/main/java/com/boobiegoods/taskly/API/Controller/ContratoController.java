package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.ContratoDTO;
import com.boobiegoods.taskly.API.Service.ContratoService;
import com.boobiegoods.taskly.Domain.Contrato;
import com.boobiegoods.taskly.Domain.Pessoa;
import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {
    
    private final ContratoService contratoService;
    // Construtor para inicializar dados de exemplo
    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }
    /**
     * GET /api/contratos - Listar todos os contratos
     */
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodosContratos() {
        List<ContratoDTO> contratosDTO = contratos.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(contratosDTO);
    }
    
    /**
     * GET /api/contratos/{id} - Buscar contrato por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> buscarContratoPorId(@PathVariable int id) {
        Optional<Contrato> contratoOpt = contratos.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        
        if (contratoOpt.isPresent()) {
            ContratoDTO contratoDTO = converterParaDTO(contratoOpt.get());
            return ResponseEntity.ok(contratoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST /api/contratos - Criar novo contrato
     */
    @PostMapping
    public ResponseEntity<ContratoDTO> criarContrato(@RequestBody ContratoDTO contratoDTO) {
        // Buscar pessoa
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == contratoDTO.getFkPessoa())
                .findFirst();
        
        // Buscar perfil
        Optional<Perfil> perfilOpt = perfis.stream()
                .filter(p -> p.getId() == contratoDTO.getFkPerfil())
                .findFirst();
        
        if (pessoaOpt.isEmpty() || perfilOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Contrato novoContrato = new Contrato();
        novoContrato.setId(proximoId++);
        novoContrato.setPessoa(pessoaOpt.get());
        novoContrato.setPerfil(perfilOpt.get());
        novoContrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        novoContrato.setDataFimContrato(contratoDTO.getDataFimContrato());
        novoContrato.setNumeroHorasSemana(contratoDTO.getNumeroHorasSemana());
        novoContrato.setValorHora(contratoDTO.getValorHora());
        
        contratos.add(novoContrato);
        
        ContratoDTO contratoCriado = converterParaDTO(novoContrato);
        return ResponseEntity.ok(contratoCriado);
    }
    
    /**
     * GET /api/contratos/pessoa/{pessoaId} - Listar contratos de uma pessoa
     */
    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<ContratoDTO>> listarContratosPorPessoa(@PathVariable int pessoaId) {
        List<ContratoDTO> contratosDTO = contratos.stream()
                .filter(c -> c.getPessoa().getId() == pessoaId)
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(contratosDTO);
    }
    
    /**
     * GET /api/contratos/perfil/{perfilId} - Listar contratos por perfil
     */
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<ContratoDTO>> listarContratosPorPerfil(@PathVariable int perfilId) {
        List<ContratoDTO> contratosDTO = contratos.stream()
                .filter(c -> c.getPerfil().getId() == perfilId)
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(contratosDTO);
    }
    
    /**
     * GET /api/contratos/ativos - Listar contratos ativos (data fim >= hoje)
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<ContratoDTO>> listarContratosAtivos() {
        LocalDate hoje = LocalDate.now();
        List<ContratoDTO> contratosAtivos = contratos.stream()
                .filter(c -> c.getDataFimContrato().isAfter(hoje) || c.getDataFimContrato().isEqual(hoje))
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(contratosAtivos);
    }
    
    /**
     * GET /api/contratos/{id}/custo-total - Calcular custo total do contrato
     */
    @GetMapping("/{id}/custo-total")
    public ResponseEntity<Double> calcularCustoTotalContrato(@PathVariable int id) {
        Optional<Contrato> contratoOpt = contratos.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            
            // Calcular número de semanas entre data início e fim
            long dias = contrato.getDataInicioContrato().datesUntil(contrato.getDataFimContrato().plusDays(1)).count();
            double semanas = dias / 7.0;
            
            // Custo total = horas por semana * valor hora * número de semanas
            double custoTotal = contrato.getNumeroHorasSemana() * contrato.getValorHora() * semanas;
            
            return ResponseEntity.ok(custoTotal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para converter Contrato para ContratoDTO
    private ContratoDTO converterParaDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setId(contrato.getId());
        dto.setFkPessoa(contrato.getPessoa().getId());
        dto.setFkPerfil(contrato.getPerfil().getId());
        dto.setDataInicioContrato(contrato.getDataInicioContrato());
        dto.setDataFimContrato(contrato.getDataFimContrato());
        dto.setNumeroHorasSemana(contrato.getNumeroHorasSemana());
        dto.setValorHora(contrato.getValorHora());
        return dto;
    }
    
    // Inicializar dados de teste
    
}
