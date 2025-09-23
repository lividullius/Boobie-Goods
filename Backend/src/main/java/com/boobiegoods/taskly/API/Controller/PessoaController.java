package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.PessoaDTO;
import com.boobiegoods.taskly.Domain.Pessoa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {
    
    // Lista simulada para desenvolvimento
    private List<Pessoa> pessoas = new ArrayList<>();
    private int proximoId = 1;
    
    // Construtor para inicializar dados de exemplo
    public PessoaController() {
        inicializarDadosDeTeste();
    }
    
    /**
     * GET /api/pessoas - Listar todas as pessoas
     */
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas() {
        List<PessoaDTO> pessoasDTO = pessoas.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(pessoasDTO);
    }
    
    /**
     * GET /api/pessoas/{id} - Buscar pessoa por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable int id) {
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
        if (pessoaOpt.isPresent()) {
            PessoaDTO pessoaDTO = converterParaDTO(pessoaOpt.get());
            return ResponseEntity.ok(pessoaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST /api/pessoas - Criar nova pessoa
     */
    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setId(proximoId++);
        novaPessoa.setNome(pessoaDTO.getNome());
        
        pessoas.add(novaPessoa);
        
        PessoaDTO pessoaCriada = converterParaDTO(novaPessoa);
        return ResponseEntity.ok(pessoaCriada);
    }
    
    /**
     * GET /api/pessoas/{id}/perfis - Listar perfis de uma pessoa
     */
    @GetMapping("/{id}/perfis")
    public ResponseEntity<List<String>> listarPerfisDeUmaPessoa(@PathVariable int id) {
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
        if (pessoaOpt.isPresent()) {
            // TODO: Implementar busca real dos perfis
            List<String> perfis = new ArrayList<>();
            perfis.add("Desenvolvedor");
            perfis.add("Analista");
            return ResponseEntity.ok(perfis);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/pessoas/{id}/projetos - Listar projetos de uma pessoa
     */
    @GetMapping("/{id}/projetos")
    public ResponseEntity<List<String>> listarProjetosDeUmaPessoa(@PathVariable int id) {
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
        if (pessoaOpt.isPresent()) {
            // TODO: Implementar busca real dos projetos através das alocações
            List<String> projetos = new ArrayList<>();
            projetos.add("Sistema Web");
            projetos.add("App Mobile");
            return ResponseEntity.ok(projetos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para converter Pessoa para PessoaDTO
    private PessoaDTO converterParaDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        return dto;
    }
    
    // Inicializar dados de teste
    private void inicializarDadosDeTeste() {
        pessoas.add(new Pessoa(proximoId++, "João Silva"));
        pessoas.add(new Pessoa(proximoId++, "Maria Santos"));
        pessoas.add(new Pessoa(proximoId++, "Pedro Oliveira"));
        pessoas.add(new Pessoa(proximoId++, "Ana Costa"));
        pessoas.add(new Pessoa(proximoId++, "Carlos Ferreira"));
    }
}
