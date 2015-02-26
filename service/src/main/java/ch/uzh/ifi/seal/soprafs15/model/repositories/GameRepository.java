package ch.uzh.ifi.seal.soprafs15.model.repositories;

import ch.uzh.ifi.seal.soprafs15.model.Game;
import ch.uzh.ifi.seal.soprafs15.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public interface GameRepository extends CrudRepository<Game, Long> {
	User findByName(String name);
}
