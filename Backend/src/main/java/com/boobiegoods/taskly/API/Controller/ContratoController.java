package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.ContratoDTO;
import com.boobiegoods.taskly.API.Service.ContratoService;
import com.boobiegoods.taskly.Data.Repository.ContratoRepository;
import com.boobiegoods.taskly.Data.Repository.PerfilRepository;
import com.boobiegoods.taskly.Data.Repository.PessoaRepository;
import com.boobiegoods.taskly.Domain.Contrato;
import com.boobiegoods.taskly.Domain.Pessoa;
import com.boobiegoods.taskly.Domain.Perfil;
import com.boobiegoods.taskly.Domain.TipoPerfil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {
    
    private final ContratoService contratoService;
    private final PessoaRepository pessoaRepository;
    private final PerfilRepository perfilRepository;
    private final ContratoRepository contratoRepository;



    // Construtor para inicializar dados de exemplo
    public ContratoController(ContratoService contratoService, PessoaRepository pessoaRepository, PerfilRepository perfilRepository, ContratoRepository contratoRepository) {
        this.contratoService = contratoService;
        this.pessoaRepository = pessoaRepository;
        this.perfilRepository = perfilRepository;
        this.contratoRepository = contratoRepository;
    }
    /**
     * GET /api/contratos - Listar todos os contratos
     */
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodosContratos() {
        List<ContratoDTO> contratosDTO = contratoService.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(contratosDTO);
    }
    
    /**
     * GET /api/contratos/{id} - Buscar contrato por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> buscarContratoPorId(@PathVariable int id) {
        Optional<Contrato> contratoOpt = contratoService.findById(id);

        return contratoOpt.map(c -> ResponseEntity.ok(converterParaDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
                
    
    /**
     * POST /api/contratos - Criar novo contrato
     */
    @PostMapping
    public ResponseEntity<ContratoDTO> criarContrato(@RequestBody ContratoDTO contratoDTO) {
        Pessoa pessoa = pessoaRepository.findById(contratoDTO.getFkPessoa())
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Perfil perfil = perfilRepository.findById(contratoDTO.getFkPerfil())
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado para a pessoa"));

        if(contratoDTO.getDataFimContrato().isBefore(contratoDTO.getDataInicioContrato())) {
            throw new RuntimeException("Data de final não pode ser maior que a data de inicio do contrato");
        }

        if(contratoDTO.getNumeroHorasSemana() > 40) {
            throw new RuntimeException("O número de horas semanais não pode ser superior a 40");
        }

        if(contratoDTO.getNumeroHorasSemana() < 0) {
            throw new RuntimeException("O número de horas semanais não pode ser negativo");
        }
        if(contratoDTO.getSalarioHora().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O valor do salário por hora não pode ser negativo");
        }

        Contrato contrato = new Contrato();
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDataFimContrato(contratoDTO.getDataFimContrato());
        contrato.setNumeroHorasSemana(contratoDTO.getNumeroHorasSemana());
        contrato.setSalarioHora(contratoDTO.getSalarioHora());
        contrato.setPerfil(perfil);
        contrato.setPessoa(pessoa);

        Contrato contratoSalvo = contratoRepository.save(contrato);

        return ResponseEntity.ok(converterParaDTO(contratoSalvo));
    
    }

    private ContratoDTO converterParaDTO(Contrato contrato) {
        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setDataInicioContrato(contrato.getDataInicioContrato());
        contratoDTO.setDataFimContrato(contrato.getDataFimContrato());
        contratoDTO.setNumeroHorasSemana(contrato.getNumeroHorasSemana());
        contratoDTO.setSalarioHora(contrato.getSalarioHora());
        contratoDTO.setFkPerfil(contrato.getPerfil().getId());
        contratoDTO.setFkPessoa(contrato.getPessoa().getId());

        return contratoDTO;
    }


    
    /**
     * GET /api/contratos/pessoa/{pessoaId} - Listar contratos de uma pessoa
     */
    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<ContratoDTO>> listarContratosPorPessoa(@PathVariable int pessoaId) {
        List<ContratoDTO> contratosDTO = contratoService.findAll().stream()
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
        List<ContratoDTO> contratosDTO = contratoService.findAll().stream()
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
        List<ContratoDTO> contratosAtivos = contratoService.findAll().stream()
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
        Optional<Contrato> contratoOpt = contratoService.findAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            
            // Calcular número de semanas entre data início e fim
            long dias = contrato.getDataInicioContrato().datesUntil(contrato.getDataFimContrato().plusDays(1)).count();
            double semanas = dias / 7.0;
            BigDecimal horaSemanaBigDecimal = BigDecimal.valueOf(contrato.getNumeroHorasSemana());
            BigDecimal semanasBigDecimal = BigDecimal.valueOf(semanas);
            BigDecimal salarioHorBigDecimal = contrato.getSalarioHora();
            
            // Custo total = horas por semana * valor hora * número de semanas
            BigDecimal custoTotal = salarioHorBigDecimal.multiply(semanasBigDecimal).multiply(horaSemanaBigDecimal);            
            return ResponseEntity.ok(custoTotal.doubleValue());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para converter Contrato para ContratoDTO
    // private ContratoDTO converterParaDTO(Contrato contrato) {
    //     ContratoDTO dto = new ContratoDTO();
    //     dto.setId(contrato.getId());
    //     dto.setFkPessoa(contrato.getPessoa().getId());
    //     dto.setFkPerfil(contrato.getPerfil().getId());
    //     dto.setDataInicioContrato(contrato.getDataInicioContrato());
    //     dto.setDataFimContrato(contrato.getDataFimContrato());
    //     dto.setNumeroHorasSemana(contrato.getNumeroHorasSemana());
    //     dto.setValorHora(contrato.getValorHora());
    //     return dto;
    // }
    
    // Inicializar dados de teste
    
}
