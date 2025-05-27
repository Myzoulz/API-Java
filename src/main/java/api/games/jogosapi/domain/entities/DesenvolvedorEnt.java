package api.games.jogosapi.domain.entities;

import api.games.jogosapi.requests.AtualizacaoDesenvolvedorRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;

@Entity(name = "Desenvolvedor")
@Table(name = "desenvolvedores")
@EqualsAndHashCode(of = "id")
public class DesenvolvedorEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataFundacao;
    private @URL String website;
    private String sede;

    // Construtor padrão necessário para o Hibernate
    public DesenvolvedorEnt() {}

    public DesenvolvedorEnt(Long id, String nome, LocalDate dataFundacao, String website, String sede) {
        this.id = id;
        this.nome = nome;
        this.dataFundacao = dataFundacao;
        this.website = website;
        this.sede = sede;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getDataFundacao() { return dataFundacao; }
    public String getWebsite() { return website; }
    public String getSede() { return sede; }

    public void atualizarInformacoes(AtualizacaoDesenvolvedorRequest request) {
        if (request.nome() != null) this.nome = request.nome();
        if (request.dataFundacao() != null) this.dataFundacao = request.dataFundacao();
        if (request.website() != null) this.website = request.website();
        if (request.sede() != null) this.sede = request.sede();
    }

}