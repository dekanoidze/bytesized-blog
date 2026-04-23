package ge.tsu.ByteSized.repository;

import ge.tsu.ByteSized.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
