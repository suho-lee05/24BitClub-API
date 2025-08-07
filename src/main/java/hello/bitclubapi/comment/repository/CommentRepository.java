package hello.bitclubapi.comment.repository;

import ch.qos.logback.core.status.Status;
import hello.bitclubapi.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //특정 게시글의 모든 댓글 조회
    List<Comment> findAllByPost_Id(Long postId);

    long countByPost_Id(Long postId);

}
