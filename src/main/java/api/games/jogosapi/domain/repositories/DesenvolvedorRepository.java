package api.games.jogosapi.domain.repositories;

import api.games.jogosapi.domain.entities.DesenvolvedorEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesenvolvedorRepository extends JpaRepository<DesenvolvedorEnt, Long> {
}
