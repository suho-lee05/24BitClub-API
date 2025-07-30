package hello.bitclubapi.comment.controller;

import hello.bitclubapi.comment.entity.Comment;
import hello.bitclubapi.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /** 1) 게시글 댓글 전체 조회*/
    @GetMapping
    public List<Comment> list (@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    /** 2) 댓글 작성 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create (@PathVariable Long postId, @RequestParam Long userId, @RequestBody String content) {
        return commentService.addComment(userId, postId, content);
    }

    /** 3) 댓글 수정 */
    @PutMapping("/{commentId}")
    public Comment update(@PathVariable Long postId,
                          @PathVariable Long commentId,
                          @RequestParam Long userId,
                          @RequestBody String content) {
        return commentService.updateComment(commentId, userId, content);
    }

    /** 4) 댓글 삭제 (soft delete) */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId,
                       @PathVariable Long commentId,
                       @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
    }

}
