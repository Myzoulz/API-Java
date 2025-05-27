package api.games.jogosapi.requests;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AtualizacaoConsoleRequest(
        @NotNull Long id,
        String nome,
        LocalDate dataLancamento,
        String empresa
)
{

}