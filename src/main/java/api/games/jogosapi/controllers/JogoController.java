package api.games.jogosapi.controllers;

import api.games.jogosapi.domain.entities.ConsoleEnt;
import api.games.jogosapi.domain.entities.DesenvolvedorEnt;
import api.games.jogosapi.domain.entities.JogoEnt;
import api.games.jogosapi.domain.repositories.ConsoleRepository;
import api.games.jogosapi.domain.repositories.DesenvolvedorRepository;
import api.games.jogosapi.domain.repositories.JogoRepository;
import api.games.jogosapi.requests.AtualizacaoJogoRequest;
import api.games.jogosapi.requests.CadastroJogoRequest;
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
import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private DesenvolvedorRepository desenvolvedorRepository;

    @Autowired
    private ConsoleRepository consoleRepository;

    @Operation(
            summary = "Adiciona um novo jogo",
            description = "Cadastra um novo jogo no sistema"
    )
    @PostMapping
    @Transactional
    public ResponseEntity<JogoEnt> cadastrar(@RequestBody @Valid CadastroJogoRequest request) {
        DesenvolvedorEnt desenvolvedor = desenvolvedorRepository.findById(request.desenvolvedorId())
                .orElseThrow(() -> new IllegalArgumentException("Desenvolvedor não encontrado"));

        List<ConsoleEnt> consoles = consoleRepository.findAllById(request.consoleIds());

        JogoEnt jogo = new JogoEnt(
                null,
                request.nome(),
                request.descricao(),
                request.dataLancamento(),
                request.website(),
                request.genero(),
                request.urlcapa(),
                desenvolvedor,
                consoles
        );

        jogoRepository.save(jogo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(jogo.getId())
                .toUri();

        return ResponseEntity.created(location).body(jogo);
    }

    @Operation(
            summary = "Lista todos os jogos",
            description = "Retorna uma página de jogos cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<JogoEnt>> listarTodos(
            @Parameter(description = "Número da página (começa em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) @RequestParam(required = false) String sort
    ) {
        Sort sortObj = (sort != null && !sort.isBlank()) ? Sort.by(sort.split(",")) : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, size, sortObj);
        return ResponseEntity.ok(jogoRepository.findAll(pageable));
    }

    @Operation(
            summary = "Busca um jogo pelo ID",
            description = "Retorna o jogo correspondente ao ID fornecido"
    )
    @GetMapping("/{id}")
    public ResponseEntity<JogoEnt> buscarPorId(@PathVariable Long id) {
        var jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        return ResponseEntity.ok(jogo);
    }

    @Operation(
            summary = "Atualiza um jogo",
            description = "Atualiza as informações de um jogo existente"
    )
    @PutMapping
    @Transactional
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtualizacaoJogoRequest request) {
        var jogo = jogoRepository.getReferenceById(request.id());

        DesenvolvedorEnt desenvolvedor = null;
        if (request.desenvolvedorId() != null) {
            desenvolvedor = desenvolvedorRepository.findById(request.desenvolvedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Desenvolvedor não encontrado"));
        }

        List<ConsoleEnt> consoles = null;
        if (request.consoleIds() != null) {
            consoles = consoleRepository.findAllById(request.consoleIds());
        }

        jogo.atualizarInformacoes(request, desenvolvedor, consoles);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Exclui um jogo pelo ID",
            description = "Exclui um jogo existente pelo ID fornecido, removendo-o permanentemente do sistema"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        jogoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}