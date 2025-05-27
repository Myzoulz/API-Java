package api.games.jogosapi.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;

public record CadastroDesenvolvedorRequest(
       @NotBlank
       String nome,
       @NotNull @PastOrPresent
       LocalDate dataFundacao,
       @NotBlank @URL
       String website,
       @NotBlank
       String sede
)
{
}
