package api.games.jogosapi.requests;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AtualizacaoDesenvolvedorRequest(
        @NotNull Long id,
        String nome,
        LocalDate dataFundacao,
        @URL String website,
        String sede
)
{

}
