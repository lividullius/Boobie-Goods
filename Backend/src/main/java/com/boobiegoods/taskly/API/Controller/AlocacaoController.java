package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.AlocacaoDTO;
import com.boobiegoods.taskly.Domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alocacoes")
public class AlocacaoController {
    
    // Listas simuladas para desenvolvimento
    private List<Alocacao> alocacoes = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();
    private List<Contrato> contratos = new ArrayList<>();
    private List<Pessoa> pessoas = new ArrayList<>();
    private int proximoId = 1;
    
    // Construtor para inicializar dados de exemplo
    public AlocacaoController() {
        inicializarDadosDeTeste();
    }
    
    /**
     * GET /api/alocacoes - Listar todas as alocações
     */
    @GetMapping
    public ResponseEntity<List<AlocacaoDTO>> listarTodasAlocacoes() {
        List<AlocacaoDTO> alocacoesDTO = alocacoes.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(alocacoesDTO);
    }
    
    /**
     * GET /api/alocacoes/{id} - Buscar alocação por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlocacaoDTO> buscarAlocacaoPorId(@PathVariable int id) {
        Optional<Alocacao> alocacaoOpt = alocacoes.stream()
                .filter(a -> a.getIdAlocacao() == id)
                .findFirst();
        
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
        Optional<Projeto> projetoOpt = projetos.stream()
                .filter(p -> p.getId() == alocacaoDTO.getFkProjeto())
                .findFirst();
        
        // Buscar contrato
        Optional<Contrato> contratoOpt = contratos.stream()
                .filter(c -> c.getId() == alocacaoDTO.getFkContrato())
                .findFirst();
        
        // Buscar pessoa
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == alocacaoDTO.getFkPessoa())
                .findFirst();
        
        if (projetoOpt.isEmpty() || contratoOpt.isEmpty() || pessoaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validar se a pessoa já está alocada neste projeto
        boolean jaAlocada = alocacoes.stream()
                .anyMatch(a -> a.getPessoa().getId() == alocacaoDTO.getFkPessoa() 
                          && a.getProjeto().getId() == alocacaoDTO.getFkProjeto());
        
        if (jaAlocada) {
            return ResponseEntity.badRequest().build();
        }
        
        Alocacao novaAlocacao = new Alocacao();
        novaAlocacao.setIdAlocacao(proximoId++);
        novaAlocacao.setProjeto(projetoOpt.get());
        novaAlocacao.setContrato(contratoOpt.get());
        novaAlocacao.setPessoa(pessoaOpt.get());
        novaAlocacao.setHorasSemanal(alocacaoDTO.getHorasSemanal());
        
        alocacoes.add(novaAlocacao);
        
        AlocacaoDTO alocacaoCriada = converterParaDTO(novaAlocacao);
        return ResponseEntity.ok(alocacaoCriada);
    }
    
    /**
     * GET /api/alocacoes/projeto/{projetoId} - Listar alocações de um projeto
     */
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<AlocacaoDTO>> listarAlocacoesPorProjeto(@PathVariable int projetoId) {
        List<AlocacaoDTO> alocacoesDTO = alocacoes.stream()
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
        List<AlocacaoDTO> alocacoesDTO = alocacoes.stream()
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
        List<AlocacaoDTO> alocacoesDTO = alocacoes.stream()
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
        int horasTotais = alocacoes.stream()
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
        int horasTotais = alocacoes.stream()
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
        int horasTotais = alocacoes.stream()
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
    
    // Inicializar dados de teste
    private void inicializarDadosDeTeste() {
        // Criar pessoas de exemplo
        pessoas.add(new Pessoa(1, "João Silva"));
        pessoas.add(new Pessoa(2, "Maria Santos"));
        pessoas.add(new Pessoa(3, "Pedro Oliveira"));
        
        // Criar projetos de exemplo
        Projeto projeto1 = new Projeto();
        projeto1.setId(1);
        projeto1.setNomeProjeto("Sistema Web");
        projeto1.setDataInicioProjeto(LocalDate.of(2024, 1, 1));
        projeto1.setDataTerminoProjeto(LocalDate.of(2024, 12, 31));
        projetos.add(projeto1);
        
        Projeto projeto2 = new Projeto();
        projeto2.setId(2);
        projeto2.setNomeProjeto("App Mobile");
        projeto2.setDataInicioProjeto(LocalDate.of(2024, 3, 1));
        projeto2.setDataTerminoProjeto(LocalDate.of(2024, 8, 31));
        projetos.add(projeto2);
        
        // Criar contratos de exemplo
        Contrato contrato1 = new Contrato();
        contrato1.setId(1);
        contrato1.setPessoa(pessoas.get(0));
        contrato1.setDataInicioContrato(LocalDate.of(2024, 1, 1));
        contrato1.setDataFimContrato(LocalDate.of(2024, 12, 31));
        contrato1.setNumeroHorasSemana(40);
        contrato1.setSalarioHora(BigDecimal.valueOf(50));
        contratos.add(contrato1);
        
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setPessoa(pessoas.get(1));
        contrato2.setDataInicioContrato(LocalDate.of(2024, 3, 1));
        contrato2.setDataFimContrato(LocalDate.of(2025, 2, 28));
        contrato2.setNumeroHorasSemana(30);
        contrato2.setSalarioHora(BigDecimal.valueOf(45));
        contratos.add(contrato2);
        
        // Criar alocações de exemplo
        Alocacao alocacao1 = new Alocacao();
        alocacao1.setIdAlocacao(proximoId++);
        alocacao1.setProjeto(projeto1);
        alocacao1.setContrato(contrato1);
        alocacao1.setPessoa(pessoas.get(0));
        alocacao1.setHorasSemanal(25);
        alocacoes.add(alocacao1);
        
        Alocacao alocacao2 = new Alocacao();
        alocacao2.setIdAlocacao(proximoId++);
        alocacao2.setProjeto(projeto2);
        alocacao2.setContrato(contrato2);
        alocacao2.setPessoa(pessoas.get(1));
        alocacao2.setHorasSemanal(20);
        alocacoes.add(alocacao2);
        
        Alocacao alocacao3 = new Alocacao();
        alocacao3.setIdAlocacao(proximoId++);
        alocacao3.setProjeto(projeto1);
        alocacao3.setContrato(contrato2);
        alocacao3.setPessoa(pessoas.get(1));
        alocacao3.setHorasSemanal(10);
        alocacoes.add(alocacao3);
    }
}
