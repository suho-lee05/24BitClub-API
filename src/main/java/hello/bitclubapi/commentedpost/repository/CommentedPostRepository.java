package hello.bitclubapi.commentedpost.repository;

import hello.bitclubapi.commentedpost.entity.CommentedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentedPostRepository extends JpaRepository<CommentedPost, Long> {
    Optional<CommentedPost> findByUser_IdAndPost_Id(Long userId, Long postId);
    List<CommentedPost> findAllByUser_Id(Long userId);

}
