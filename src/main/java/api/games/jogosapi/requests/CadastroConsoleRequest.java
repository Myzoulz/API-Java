package api.games.jogosapi.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record CadastroConsoleRequest(
       @NotBlank
       String nome,
       @NotNull @PastOrPresent
       LocalDate dataLancamento,
       @NotBlank
       String empresa
)
{
}
