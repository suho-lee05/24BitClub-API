package hello.bitclubapi.likepost.repository;

import hello.bitclubapi.likepost.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByPostIdAndUserId(Long postId, Long userId);
    int countByPostId(Long postId);
}
