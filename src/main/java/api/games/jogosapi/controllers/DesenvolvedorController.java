package api.games.jogosapi.controllers;

import api.games.jogosapi.domain.entities.DesenvolvedorEnt;
import api.games.jogosapi.domain.repositories.DesenvolvedorRepository;
import api.games.jogosapi.requests.AtualizacaoDesenvolvedorRequest;
import api.games.jogosapi.requests.CadastroDesenvolvedorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/desenvolvedores")
public class DesenvolvedorController {

    @Autowired
    private DesenvolvedorRepository repository;

    @Operation(
            summary = "Adiciona um novo desenvolvedor",
            description = "Cadastra um novo desenvolvedor no sistema"
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DesenvolvedorEnt> cadastrar(@RequestBody @Valid CadastroDesenvolvedorRequest request) {
        DesenvolvedorEnt desenvolvedor = new DesenvolvedorEnt(null, request.nome(), request.dataFundacao(), request.website(), request.sede());
        repository.save(desenvolvedor);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(desenvolvedor.getId())
                .toUri();
        return ResponseEntity.created(location).body(desenvolvedor);
    }

    @Operation(
            summary = "Lista todos os desenvolvedores",
            description = "Retorna uma página de desenvolvedores cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<DesenvolvedorEnt>> listarTodos(
            @Parameter(description = "Número da página (começa em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @RequestParam(required = false) String sort
    ) {
        Sort sortObj = (sort != null && !sort.isBlank()) ? Sort.by(sort.split(",")) : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sortObj);
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @Operation(
            summary = "Lista um desenvolvedor pelo ID",
            description = "Retorna o desenvolvedor correspondente ao ID fornecido"
    )
    @GetMapping("/{id}")
    public ResponseEntity<DesenvolvedorEnt> buscarPorId(@PathVariable Long id) {
        var desenvolvedor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Desenvolvedor não encontrado"));
        return ResponseEntity.ok(desenvolvedor);
    }

    @Operation(
            summary = "Atualiza um desenvolvedor",
            description = "Atualiza as informações de um desenvolvedor existente"
    )
    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtualizacaoDesenvolvedorRequest request) {
        var desenvolvedor = repository.getReferenceById(request.id());
        desenvolvedor.atualizarInformacoes(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Exclui um desenvolvedor pelo ID",
            description = "Exclui um desenvolvedor existente pelo ID fornecido, removendo-o permanentemente do sistema"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}