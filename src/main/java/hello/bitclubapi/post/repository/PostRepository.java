package hello.bitclubapi.post.repository;

import hello.bitclubapi.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //특정 유저의 모든 게시글
    List<Post> findAllByUser_Id(Long userId);

    // 제목에 해당 키워드가 포함된 모든 Post 조회 (대소문자 구분)
    List<Post> findByTitleContaining(String keyword);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
