package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.AlocacaoDTO;
import com.boobiegoods.taskly.API.Service.AlocacaoService;
import com.boobiegoods.taskly.API.Service.ProjetoService;
import com.boobiegoods.taskly.API.Service.ContratoService;
import com.boobiegoods.taskly.API.Service.PessoaService;
import com.boobiegoods.taskly.Domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alocacoes")
public class AlocacaoController {
    
    private final AlocacaoService alocacaoService;
    private final ProjetoService projetoService;
    private final ContratoService contratoService;
    private final PessoaService pessoaService;

    //construtor
    public AlocacaoController(AlocacaoService alocacaoService, ProjetoService projetoService, ContratoService contratoService, PessoaService pessoaService) {
        this.alocacaoService = alocacaoService;
        this.projetoService = projetoService;
        this.contratoService = contratoService;
        this.pessoaService = pessoaService;
    }
    
    /**
     * GET /api/alocacoes - Listar todas as alocações
     */
    @GetMapping
    public ResponseEntity<List<AlocacaoDTO>> listarTodasAlocacoes() {
        List<AlocacaoDTO> alocacoesDTO = alocacaoService.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(alocacoesDTO);
    }
    
    /**
     * GET /api/alocacoes/{id} - Buscar alocação por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlocacaoDTO> buscarAlocacaoPorId(@PathVariable int id) {
        Optional<Alocacao> alocacaoOpt = alocacaoService.findById(id);
        
        if (alocacaoOpt.isPresent()) {
            AlocacaoDTO alocacaoDTO = converterParaDTO(alocacaoOpt.get());
            return ResponseEntity.ok(alocacaoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST /api/alocacoes - Criar nova alocação
     */
    @PostMapping
    public ResponseEntity<AlocacaoDTO> criarAlocacao(@RequestBody AlocacaoDTO alocacaoDTO) {
        // Buscar projeto
        Optional<Projeto> projetoOpt = projetoService.findById(alocacaoDTO.getFkProjeto());
        
        // Buscar contrato
        Optional<Contrato> contratoOpt = contratoService.findById(alocacaoDTO.getFkContrato());
        
        // Buscar pessoa
        Optional<Pessoa> pessoaOpt = pessoaService.findById(alocacaoDTO.getFkPessoa());
        
        if (projetoOpt.isEmpty() || contratoOpt.isEmpty() || pessoaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validar se a pessoa já está alocada neste projeto
        // Esta verificação precisará ser implementada no repositório ou serviço
        List<Alocacao> alocacoesExistentes = alocacaoService.findAll();
        boolean jaAlocada = alocacoesExistentes.stream()
                .anyMatch(a -> a.getPessoa().getId() == alocacaoDTO.getFkPessoa() 
                          && a.getProjeto().getId() == alocacaoDTO.getFkProjeto());
        
        if (jaAlocada) {
            return ResponseEntity.badRequest().build();
        }
        
        Alocacao novaAlocacao = new Alocacao();
        // Não precisa definir ID, pois o banco de dados vai gerar
        novaAlocacao.setProjeto(projetoOpt.get());
        novaAlocacao.setContrato(contratoOpt.get());
        novaAlocacao.setPessoa(pessoaOpt.get());
        novaAlocacao.setHorasSemanal(alocacaoDTO.getHorasSemanal());
        
        novaAlocacao = alocacaoService.save(novaAlocacao);
        
        AlocacaoDTO alocacaoCriada = converterParaDTO(novaAlocacao);
        return ResponseEntity.ok(alocacaoCriada);
    }
    
    /**
     * GET /api/alocacoes/projeto/{projetoId} - Listar alocações de um projeto
     */
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<AlocacaoDTO>> listarAlocacoesPorProjeto(@PathVariable int projetoId) {
        List<AlocacaoDTO> alocacoesDTO = alocacaoService.findAll().stream()
                .filter(a -> a.getProjeto().getId() == projetoId)
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(alocacoesDTO);
    }
    
    /**
     * GET /api/alocacoes/pessoa/{pessoaId} - Listar alocações de uma pessoa
     */
    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<AlocacaoDTO>> listarAlocacoesPorPessoa(@PathVariable int pessoaId) {
        List<AlocacaoDTO> alocacoesDTO = alocacaoService.findAll().stream()
                .filter(a -> a.getPessoa().getId() == pessoaId)
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(alocacoesDTO);
    }
    
    /**
     * GET /api/alocacoes/contrato/{contratoId} - Listar alocações de um contrato
     */
    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<AlocacaoDTO>> listarAlocacoesPorContrato(@PathVariable int contratoId) {
        List<AlocacaoDTO> alocacoesDTO = alocacaoService.findAll().stream()
                .filter(a -> a.getContrato().getId() == contratoId)
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(alocacoesDTO);
    }
    
    /**
     * GET /api/alocacoes/projeto/{projetoId}/horas-totais - Calcular total de horas semanais de um projeto
     */
    @GetMapping("/projeto/{projetoId}/horas-totais")
    public ResponseEntity<Integer> calcularHorasTotaisProjeto(@PathVariable int projetoId) {
        int horasTotais = alocacaoService.findAll().stream()
                .filter(a -> a.getProjeto().getId() == projetoId)
                .mapToInt(Alocacao::getHorasSemanal)
                .sum();
        return ResponseEntity.ok(horasTotais);
    }
    
    /**
     * GET /api/alocacoes/pessoa/{pessoaId}/horas-totais - Calcular total de horas semanais de uma pessoa
     */
    @GetMapping("/pessoa/{pessoaId}/horas-totais")
    public ResponseEntity<Integer> calcularHorasTotaisPessoa(@PathVariable int pessoaId) {
        int horasTotais = alocacaoService.findAll().stream()
                .filter(a -> a.getPessoa().getId() == pessoaId)
                .mapToInt(Alocacao::getHorasSemanal)
                .sum();
        return ResponseEntity.ok(horasTotais);
    }
    
    /**
     * GET /api/alocacoes/validar-limite/{pessoaId} - Validar se pessoa não excede 40h semanais
     */
    @GetMapping("/validar-limite/{pessoaId}")
    public ResponseEntity<Boolean> validarLimiteHoras(@PathVariable int pessoaId) {
        int horasTotais = alocacaoService.findAll().stream()
                .filter(a -> a.getPessoa().getId() == pessoaId)
                .mapToInt(Alocacao::getHorasSemanal)
                .sum();
        boolean dentroDoLimite = horasTotais <= 40;
        return ResponseEntity.ok(dentroDoLimite);
    }
    
    // Método auxiliar para converter Alocacao para AlocacaoDTO
    private AlocacaoDTO converterParaDTO(Alocacao alocacao) {
        AlocacaoDTO dto = new AlocacaoDTO();
        dto.setId(alocacao.getIdAlocacao());
        dto.setFkProjeto(alocacao.getProjeto().getId());
        dto.setFkContrato(alocacao.getContrato().getId());
        dto.setFkPessoa(alocacao.getPessoa().getId());
        dto.setHorasSemanal(alocacao.getHorasSemanal());
        return dto;
    }
    
    /**
     * GET /api/alocacoes/verificar/{pessoaId}/{projetoId} - Verificar se pessoa já está alocada no projeto
     */
    @GetMapping("/verificar/{pessoaId}/{projetoId}")
    public ResponseEntity<Boolean> verificarAlocacao(@PathVariable int pessoaId, @PathVariable int projetoId) {
        // Verificar se já existe alocação para essa pessoa e projeto
        List<Alocacao> alocacoesExistentes = alocacaoService.findAll();
        boolean jaAlocada = alocacoesExistentes.stream()
                .anyMatch(a -> a.getPessoa().getId() == pessoaId && a.getProjeto().getId() == projetoId);
                
        return ResponseEntity.ok(jaAlocada);
    }
}
