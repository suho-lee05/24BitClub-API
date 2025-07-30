package hello.bitclubapi.post.repository;

import hello.bitclubapi.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //특정 유저의 모든 게시글
    List<Post> findAllByUser_Id(Long userId);
}
