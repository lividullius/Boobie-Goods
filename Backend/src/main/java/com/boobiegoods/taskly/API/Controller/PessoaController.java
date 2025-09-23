package com.boobiegoods.taskly.API.Controller;

import com.boobiegoods.taskly.API.DTO.PessoaDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Exemplo de Controller com validação automática usando DTOs Records
 */
@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    // @Autowired
    // private PessoaService pessoaService;

    /**
     * POST - Criar nova pessoa com validação automática
     */
    @PostMapping
    public ResponseEntity<?> criarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO, BindingResult result) {
        
        // 🎯 Validação automática! Se houver erros, retorna automaticamente
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        // Se chegou aqui, dados são válidos!
        // Pessoa pessoa = new Pessoa(0, pessoaDTO.nome());
        // Pessoa salva = pessoaService.save(pessoa);
        // return ResponseEntity.ok(new PessoaDTO(salva));
        
        return ResponseEntity.ok("Pessoa criada com sucesso: " + pessoaDTO.nome());
    }

    /**
     * PUT - Atualizar pessoa com validação automática
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPessoa(
            @PathVariable Integer id, 
            @Valid @RequestBody PessoaDTO pessoaDTO, 
            BindingResult result) {
        
        // 🎯 Validação automática
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        // Lógica de atualização aqui
        return ResponseEntity.ok("Pessoa " + id + " atualizada: " + pessoaDTO.nome());
    }

    /**
     * GET - Listar pessoas (sem validação, apenas conversão)
     */
    @GetMapping
    public ResponseEntity<String> listarPessoas() {
        // List<Pessoa> pessoas = pessoaService.findAll();
        // List<PessoaDTO> pessoasDTO = pessoas.stream()
        //         .map(PessoaDTO::new)
        //         .toList();
        // return ResponseEntity.ok(pessoasDTO);
        
        return ResponseEntity.ok("Lista de pessoas (implementar service)");
    }
}