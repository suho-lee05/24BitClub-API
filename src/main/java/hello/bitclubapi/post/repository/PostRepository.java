package hello.bitclubapi.post.repository;

import hello.bitclubapi.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
