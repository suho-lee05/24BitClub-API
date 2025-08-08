package hello.bitclubapi.comment.repository;

import ch.qos.logback.core.status.Status;
import hello.bitclubapi.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //특정 게시글의 모든 댓글 조회
    List<Comment> findAllByPost_Id(Long postId);

    long countByPost_Id(Long postId);

    /** 특정 게시글에, 특정 사용자가 단 댓글만 조회 (내가 단 댓글 개수 계산용) */
    List<Comment> findAllByPost_IdAndUser_Id(Long postId, Long userId);

    /** ① 특정 사용자(userId)가 단 모든 댓글 조회 */
    List<Comment> findAllByUser_Id(Long userId);

    Page<Comment> findAllByPost_Id(Long postId, Pageable pageable);
}
