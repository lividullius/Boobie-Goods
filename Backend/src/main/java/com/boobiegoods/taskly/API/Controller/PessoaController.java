package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.PerfilDTO;
import com.boobiegoods.taskly.API.DTO.PessoaDTO;
import com.boobiegoods.taskly.API.Service.PessoaService;
import com.boobiegoods.taskly.API.Service.PerfilService;
import com.boobiegoods.taskly.Domain.Pessoa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final PerfilService perfilService;

    public PessoaController(PessoaService pessoaService, PerfilService perfilService) {
        this.pessoaService = pessoaService;
        this.perfilService = perfilService;
    }

    /**
     * GET /api/pessoas - Listar todas as pessoas
     */
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas() {
        List<PessoaDTO> pessoasDTO = pessoaService.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(pessoasDTO);
    }

    /**
     * GET /api/pessoas/{id} - Buscar pessoa por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscarPessoaPorId(@PathVariable int id) {
        Optional<Pessoa> pessoaOpt = pessoaService.findById(id);

        return pessoaOpt
                .map(p -> ResponseEntity.ok(converterParaDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/pessoas - Criar nova pessoa
     */
    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome(pessoaDTO.getNome());

        Pessoa salva = pessoaService.saveWithValidation(novaPessoa);
        return ResponseEntity.ok(converterParaDTO(salva));
    }

    /**
     * GET /api/pessoas/{id}/perfis - Listar perfis de uma pessoa
     */
    @GetMapping("/{id}/perfis")
    public ResponseEntity<List<PerfilDTO>> listarPerfisDeUmaPessoa(@PathVariable int id) {
        List<PerfilDTO> perfis = perfilService.findByPessoaId(id)
                .stream()
                .map(perfil -> new PerfilDTO(perfil.getId(), perfil.getTipo().toString()))
                .toList();

        if (perfis.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(perfis);
    }

    /**
     * GET /api/pessoas/{id}/projetos - Listar projetos de uma pessoa
     */
    /* @GetMapping("/{id}/projetos")
    public ResponseEntity<List<String>> listarProjetosDeUmaPessoa(@PathVariable int id) {
        Optional<Pessoa> pessoaOpt = pessoas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (pessoaOpt.isPresent()) {
            // Implementar busca real dos projetos através das alocações
            List<String> projetos = new ArrayList<>();
            projetos.add("Sistema Web");
            projetos.add("App Mobile");
            return ResponseEntity.ok(projetos);
        } else {
            return ResponseEntity.notFound().build();
        }
    } */

    // Método auxiliar para converter Pessoa para PessoaDTO
    private PessoaDTO converterParaDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO(pessoa.getId(), pessoa.getNome());
        return dto;
    }
}
