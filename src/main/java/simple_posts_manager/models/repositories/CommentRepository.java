package simple_posts_manager.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import simple_posts_manager.models.entities.Comment;



public interface CommentRepository extends CrudRepository<Comment, Long > {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comments WHERE id= ?1", nativeQuery = true)
    void deleteUsingId(Long id);
}