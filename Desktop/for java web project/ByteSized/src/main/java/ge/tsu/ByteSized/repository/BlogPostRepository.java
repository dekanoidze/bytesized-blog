package ge.tsu.ByteSized.repository;

import ge.tsu.ByteSized.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
