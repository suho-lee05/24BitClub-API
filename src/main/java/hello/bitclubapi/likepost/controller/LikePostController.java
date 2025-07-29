package hello.bitclubapi.likepost.controller;

import hello.bitclubapi.likepost.service.LikePostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like-post")
public class LikePostController {

    private final LikePostService likePostService;

    public LikePostController(LikePostService likePostService) {
        this.likePostService = likePostService;
    }

    // 좋아요 누르기
    @PostMapping("/{postId}")
    public void likePost(@PathVariable Long postId, @RequestHeader("X-USER-ID") Long userId) {
        likePostService.likePost(postId, userId);
    }

    // 좋아요 취소
    @DeleteMapping("/{postId}")
    public void unlikePost(@PathVariable Long postId, @RequestHeader("X-USER-ID") Long userId) {
        likePostService.unlikePost(postId, userId);
    }

    // 좋아요 수 조회
    @GetMapping("/{postId}")
    public int getLikeCount(@PathVariable Long postId) {
        return likePostService.getLikeCount(postId);
    }
}
