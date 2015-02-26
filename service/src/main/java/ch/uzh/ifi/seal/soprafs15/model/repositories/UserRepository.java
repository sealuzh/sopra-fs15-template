package ch.uzh.ifi.seal.soprafs15.model.repositories;

import ch.uzh.ifi.seal.soprafs15.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	User findByName(String name);
	User findByUsername(String username);
	User findByToken(String token);
}
