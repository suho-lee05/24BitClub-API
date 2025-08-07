package hello.bitclubapi.likepost.repository;

import hello.bitclubapi.likepost.entity.LikePost;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    Optional<LikePost> findByPostAndUser(Post post, User user);

    long countByPost(Post post);

    long countByPost_Id(Long postId);

}
