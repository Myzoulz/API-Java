package api.games.jogosapi.controllers;

import api.games.jogosapi.domain.entities.ConsoleEnt;
import api.games.jogosapi.domain.repositories.ConsoleRepository;
import api.games.jogosapi.requests.AtualizacaoConsoleRequest;
import api.games.jogosapi.requests.CadastroConsoleRequest;
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
@RequestMapping("/consoles")
public class ConsoleController {

    @Autowired
    private ConsoleRepository repository;

    @Operation(
            summary = "Adiciona um novo console",
            description = "Cadastra um novo console no sistema"
    )
    @PostMapping
    @Transactional
    public ResponseEntity<ConsoleEnt> cadastrar(@RequestBody @Valid CadastroConsoleRequest request) {
        ConsoleEnt console = new ConsoleEnt(null, request.nome(), request.dataLancamento(), request.empresa());
        repository.save(console);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(console.getId())
                .toUri();
        return ResponseEntity.created(location).body(console);
    }

    @Operation(
            summary = "Lista todos os consoles",
            description = "Retorna uma página de consoles cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ConsoleEnt>> listarTodos(
            @Parameter(description = "Número da página (começa em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @RequestParam(required = false) String sort
    ) {
        Sort sortObj = (sort != null && !sort.isBlank()) ? Sort.by(sort.split(",")) : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sortObj);
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @Operation(
            summary = "Busca um console por ID",
            description = "Retorna os detalhes de um console específico"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ConsoleEnt> buscarPorId(@PathVariable Long id) {
        var console = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Console não encontrado"));
        return ResponseEntity.ok(console);
    }

    @Operation(
            summary = "Atualiza um console",
            description = "Altera as informações de um console existente"
    )
    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtualizacaoConsoleRequest request) {
        var console = repository.getReferenceById(request.id());
        console.atualizarInformacoes(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Exclui um console",
            description = "Remove um console do sistema pelo ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}