package api.games.jogosapi.domain.repositories;

import api.games.jogosapi.domain.entities.JogoEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<JogoEnt, Long> {
}
