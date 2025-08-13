package hello.bitclubapi.comment.service;

import hello.bitclubapi.comment.dto.CommentDto;
import hello.bitclubapi.comment.entity.Comment;
import hello.bitclubapi.comment.repository.CommentRepository;

import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.repository.PostRepository;
import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /** 댓글 목록 조회(모든 댓글)*/
    @Transactional
   public List<Comment> getCommentsByPost(Long postId) {
       return commentRepository.findAllByPost_Id(postId);
   }

   /**댓글 생성*/
   @Transactional
    public Comment addComment(Long userId, Long postId, String content) {
       User user = userRepository.findById(userId)
               .orElseThrow(()-> new IllegalArgumentException("User가 존재하지 않습니다."));
       Post post = postRepository.findById(postId)
               .orElseThrow(()-> new IllegalArgumentException("Post가 존재하지 않습니다."));
       Comment comment = new Comment();
       comment.setUser(user);
       comment.setPost(post);
       comment.setContent(content);
       comment.setCreatedAt(LocalDateTime.now()); // ★ 직렬화용 필드 채우기
       return commentRepository.save(comment);
   }

   /** 댓글 수정 (근데 피그마엔 없음)*/
   @Transactional
    public Comment updateComment(Long commentId, Long userId,String newContent) {
       Comment comment = commentRepository.findById(commentId)
               .orElseThrow(()-> new IllegalArgumentException("Comment가 존재하지 않습니다."));
       if(comment.getUser().getId() != userId){
           throw new SecurityException("권환이 없습니다.");

       }
       comment.setContent(newContent);
       return comment;
   }

   /** 댓글 삭제(delete)*/
   @Transactional
   public void deleteComment(Long commentId, Long userId) {
       Comment comment = commentRepository.findById(commentId)
               .orElseThrow(() -> new ResponseStatusException(
                       HttpStatus.NOT_FOUND, "Comment가 존재하지 않습니다."));

       if (!comment.getUser().getId().equals(userId)) {
           throw new ResponseStatusException(
                   HttpStatus.FORBIDDEN, "권한이 없습니다.");
       }
       commentRepository.delete(comment);
   }


    /** 게시글 상세: 댓글 전체 목록 (페이징 없음) */
    public List<CommentDto> getCommentsForPost(Long postId, String sort, Long viewerUserId) {
        boolean asc = "asc".equalsIgnoreCase(sort);

        List<Comment> comments = asc
                ? commentRepository.findAllByPost_IdOrderByCreatedAtAsc(postId)
                : commentRepository.findAllByPost_IdOrderByCreatedAtDesc(postId);

        return comments.stream().map(c -> {
            CommentDto dto = new CommentDto();
            dto.setCommentId(c.getId());
            dto.setUserId(c.getUser().getId());
            dto.setUsername(c.getUser().getUsername());
            dto.setContent(c.getContent());
            dto.setCreatedAt(c.getCreatedAt());
            dto.setMine(viewerUserId != null && c.getUser().getId().equals(viewerUserId));
            return dto;
        }).toList();
    }



}
