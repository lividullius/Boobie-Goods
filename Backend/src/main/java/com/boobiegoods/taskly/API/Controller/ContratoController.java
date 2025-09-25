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
            .orElseThrow(() -> new RuntimeException("Pessoa não encontrado"));
        Perfil perfil = perfilRepository.findById(contratoDTO.getFkPerfil())
            .orElseThrow(() -> new RuntimeException("Perfil não encontrado para a pessoa"));

        if(contratoDTO.getDataFimContrato().isBefore(contratoDTO.getDataInicioContrato())) {
            throw new RuntimeException("Data de final não pode ser maior que a data de inicio do contrato");
        }
<<<<<<< HEAD

        if(contratoDTO.getNumeroHorasSemana() > 40) {
            throw new RuntimeException("O número de horas semanais não pode ser superior a 40");
        }

        if(contratoDTO.getNumeroHorasSemana() < 0) {
            throw new RuntimeException("O número de horas semanais não pode ser negativo");
        }
        if(contratoDTO.getValorporHora().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O valor do salário por hora não pode ser negativo");
        }

        Contrato contrato = new Contrato();
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDataFimContrato(contratoDTO.getDataFimContrato());
        contrato.setNumeroHorasSemana(contratoDTO.getNumeroHorasSemana());
        contrato.setValorporHora(contratoDTO.getValorporHora());
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
        contratoDTO.setValorporHora(contrato.getValorporHora());
        contratoDTO.setFkPerfil(contrato.getPerfil().getId());
        contratoDTO.setFkPessoa(contrato.getPessoa().getId());

        return contratoDTO;
    }

    


=======
        
        Contrato novoContrato = new Contrato();
        novoContrato.setId(proximoId++);
        novoContrato.setPessoa(pessoaOpt.get());
        novoContrato.setPerfil(perfilOpt.get());
        novoContrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        novoContrato.setDataFimContrato(contratoDTO.getDataFimContrato());
        novoContrato.setNumeroHorasSemana(contratoDTO.getNumeroHorasSemana());
        novoContrato.setSalarioHora(contratoDTO.getSalarioHora());
        
        contratos.add(novoContrato);
        
        ContratoDTO contratoCriado = converterParaDTO(novoContrato);
        return ResponseEntity.ok(contratoCriado);
>>>>>>> ebfc69c7d6a9467912cad0420310d47a7a4bc41c
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
<<<<<<< HEAD
        
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
    
=======

        if (contratoOpt.isEmpty()) return ResponseEntity.notFound().build();

        Contrato contrato = contratoOpt.get();

        LocalDate ini = contrato.getDataInicioContrato();
        LocalDate fim = contrato.getDataFimContrato();

        long dias = ChronoUnit.DAYS.between(ini, fim) + 1; // inclusivo

        // horas/dia = horas/semana / 7
        BigDecimal horasDia = BigDecimal.valueOf(contrato.getNumeroHorasSemana())
                .divide(BigDecimal.valueOf(7), 6, RoundingMode.HALF_UP);

        BigDecimal custoTotal = contrato.getSalarioHora()
                .multiply(horasDia)
                .multiply(BigDecimal.valueOf(dias))
                .setScale(2, RoundingMode.HALF_UP);

        return ResponseEntity.ok(custoTotal);
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
        dto.setSalarioHora(contrato.getSalarioHora());
        return dto;
    }
    
    // Inicializar dados de teste
    private void inicializarDadosDeTeste() {
        // Criar pessoas de exemplo
        pessoas.add(new Pessoa(1, "João Silva"));
        pessoas.add(new Pessoa(2, "Maria Santos"));
        pessoas.add(new Pessoa(3, "Pedro Oliveira"));
        
        // Criar perfis de exemplo
        perfis.add(new Perfil(1, TipoPerfil.Developer));
        perfis.add(new Perfil(2, TipoPerfil.QualityAnalyst));
        perfis.add(new Perfil(3, TipoPerfil.Gerente));
        
        // Criar contratos de exemplo
        Contrato contrato1 = new Contrato();
        contrato1.setId(proximoId++);
        contrato1.setPessoa(pessoas.get(0));
        contrato1.setPerfil(perfis.get(0));
        contrato1.setDataInicioContrato(LocalDate.of(2024, 1, 1));
        contrato1.setDataFimContrato(LocalDate.of(2024, 12, 31));
        contrato1.setNumeroHorasSemana(40);
        contrato1.setSalarioHora(BigDecimal.valueOf(50));
        contratos.add(contrato1);
        
        Contrato contrato2 = new Contrato();
        contrato2.setId(proximoId++);
        contrato2.setPessoa(pessoas.get(1));
        contrato2.setPerfil(perfis.get(1));
        contrato2.setDataInicioContrato(LocalDate.of(2024, 3, 1));
        contrato2.setDataFimContrato(LocalDate.of(2025, 2, 28));
        contrato2.setNumeroHorasSemana(30);
        contrato2.setSalarioHora(BigDecimal.valueOf(45));
        contratos.add(contrato2);
        
        Contrato contrato3 = new Contrato();
        contrato3.setId(proximoId++);
        contrato3.setPessoa(pessoas.get(2));
        contrato3.setPerfil(perfis.get(2));
        contrato3.setDataInicioContrato(LocalDate.of(2024, 6, 1));
        contrato3.setDataFimContrato(LocalDate.of(2024, 11, 30));
        contrato3.setNumeroHorasSemana(40);
        contrato3.setSalarioHora(BigDecimal.valueOf(80));
        contratos.add(contrato3);
    }
>>>>>>> ebfc69c7d6a9467912cad0420310d47a7a4bc41c
}
