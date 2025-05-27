package api.games.jogosapi.requests;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

public record AtualizacaoJogoRequest(
        @NotNull Long id,
        String nome,
        String descricao,
        LocalDate dataLancamento,
        @URL String website,
        Long desenvolvedorId,
        String genero,
        @URL String urlcapa,
        List<Long> consoleIds
)
{
}
