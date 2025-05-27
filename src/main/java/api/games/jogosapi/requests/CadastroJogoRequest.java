package api.games.jogosapi.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;
import java.util.List;

public record CadastroJogoRequest(
       @NotBlank
       String nome,
       @NotBlank
       String descricao,
       @NotNull @PastOrPresent
       LocalDate dataLancamento,
       @NotBlank @URL
       String website,
       @NotNull
       Long desenvolvedorId,
       @NotBlank
       String genero,
       @NotBlank @URL
       String urlcapa,
       @NotNull
       List<Long> consoleIds
)
{
}
