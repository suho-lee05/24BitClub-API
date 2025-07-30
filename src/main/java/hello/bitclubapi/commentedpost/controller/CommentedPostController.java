package hello.bitclubapi.commentedpost.controller;

import hello.bitclubapi.commentedpost.entity.CommentedPost;
import hello.bitclubapi.commentedpost.service.CommentedPostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 엔티티를 직접 주고받는 간단한 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/comments")
public class CommentedPostController {

    private final CommentedPostService commentedPostService;

    public CommentedPostController(CommentedPostService commentedPostService) {
        this.commentedPostService = commentedPostService;
    }

    /**특정 사용자-게시글 관계 조회*/
    @GetMapping("/user/{userId}/post/{postId}")
    public CommentedPost getOne(@PathVariable Long userId, @PathVariable Long postId) {
        return commentedPostService.getOne(userId, postId);
    }

    /** 사용자별 전체 목록*/
    @GetMapping("/user/{userId}")
    public List<CommentedPost> getAll(@PathVariable Long userId) {
        return commentedPostService.listByUser(userId);
    }

    /** 생성/업데이트 (엔티티 직접 전달)*/
    @PostMapping
    public CommentedPost createOrUpdate(@RequestBody CommentedPost commentedPost) {
        return commentedPostService.upsert(
                commentedPost.getUser().getId(),
                commentedPost.getPost().getId(),
                commentedPost.getMyCommentCount(),
                commentedPost.getTotalCommentCount(),
                commentedPost.getLastCommentedAt() != null ? commentedPost.getLastCommentedAt() : LocalDateTime.now()

        );
    }

    /** 삭제 */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        commentedPostService.delete(id);
    }




}
