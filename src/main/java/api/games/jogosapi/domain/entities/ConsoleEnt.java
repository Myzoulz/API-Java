package api.games.jogosapi.domain.entities;

import api.games.jogosapi.requests.AtualizacaoConsoleRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity(name = "Console")
@Table(name = "consoles")
@EqualsAndHashCode(of = "id")
public class ConsoleEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataLancamento;
    private String empresa;

    public ConsoleEnt() {}

    public ConsoleEnt(Long id, String nome, LocalDate dataLancamento, String empresa) {
        this.id = id;
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.empresa = empresa;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getDataLancamento() { return dataLancamento; }
    public String getEmpresa() { return empresa; }

    public void atualizarInformacoes(AtualizacaoConsoleRequest request) {
        if (request.nome() != null) this.nome = request.nome();
        if (request.dataLancamento() != null) this.dataLancamento = request.dataLancamento();
        if (request.empresa() != null) this.empresa = request.empresa();
    }
}