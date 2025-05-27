package api.games.jogosapi.domain.entities;

import api.games.jogosapi.requests.AtualizacaoJogoRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Jogo")
@Table(name = "jogos")
@EqualsAndHashCode(of = "id")
public class JogoEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDate dataLancamento;
    private @URL String website;
    private String genero;
    private @URL String urlcapa;

    @ManyToOne
    @JoinColumn(name = "desenvolvedor_id")
    private DesenvolvedorEnt desenvolvedor;

    @ManyToMany
    @JoinTable(
            name = "jogo_console",
            joinColumns = @JoinColumn(name = "jogo_id"),
            inverseJoinColumns = @JoinColumn(name = "console_id")
    )
    private List<ConsoleEnt> consoles;

    // Construtor padrão explícito
    public JogoEnt() {}

    public JogoEnt(Long id, String nome, String descricao, LocalDate dataLancamento, String website, String genero, String urlcapa, DesenvolvedorEnt desenvolvedor, List<ConsoleEnt> consoles) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.website = website;
        this.genero = genero;
        this.urlcapa = urlcapa;
        this.desenvolvedor = desenvolvedor;
        this.consoles = consoles;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataLancamento() { return dataLancamento; }
    public String getWebsite() { return website; }
    public String getGenero() { return genero; }
    public String getUrlcapa() { return urlcapa; }
    public DesenvolvedorEnt getDesenvolvedor() { return desenvolvedor; }
    public List<ConsoleEnt> getConsoles() { return consoles; }

    public void atualizarInformacoes(@Valid AtualizacaoJogoRequest request, DesenvolvedorEnt desenvolvedor, List<ConsoleEnt> consoles) {
        if (request.nome() != null) this.nome = request.nome();
        if (request.descricao() != null) this.descricao = request.descricao();
        if (request.dataLancamento() != null) this.dataLancamento = request.dataLancamento();
        if (request.website() != null) this.website = request.website();
        if (request.genero() != null) this.genero = request.genero();
        if (request.urlcapa() != null) this.urlcapa = request.urlcapa();
        if (desenvolvedor != null) this.desenvolvedor = desenvolvedor;
        if (consoles != null) this.consoles = consoles;
    }
}