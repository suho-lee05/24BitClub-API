package hello.bitclubapi.likepost.controller;

import hello.bitclubapi.likepost.service.LikePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikePostController {

    private final LikePostService likePostService;

    public LikePostController(LikePostService likePostService) {
        this.likePostService = likePostService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> like(@PathVariable Long postId, @RequestHeader("X-USER-ID") Long userId) {
        likePostService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlike(@PathVariable Long postId, @RequestHeader("X-USER-ID") Long userId) {
        likePostService.unlikePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> count(@PathVariable Long postId) {
        long count = likePostService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }
}
