package api.games.jogosapi.domain.repositories;

import api.games.jogosapi.domain.entities.ConsoleEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsoleRepository extends JpaRepository<ConsoleEnt, Long> {
}
