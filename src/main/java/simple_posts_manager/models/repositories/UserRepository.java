package simple_posts_manager.models.repositories;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import simple_posts_manager.models.entities.User;



public interface UserRepository extends CrudRepository<User, Long > {

    Optional<User> findByEmail(String email);

}