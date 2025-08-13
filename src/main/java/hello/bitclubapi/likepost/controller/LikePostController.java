package hello.bitclubapi.likepost.controller;

import hello.bitclubapi.likepost.repository.LikePostRepository;
import hello.bitclubapi.likepost.service.LikePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.repository.UserRepository;


import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikePostController {

    private final LikePostRepository likePostRepository;
    private final LikePostService likePostService;
    private final UserRepository userRepository;


    public LikePostController(LikePostRepository likePostRepository, LikePostService likePostService, UserRepository userRepository) {
        this.likePostRepository = likePostRepository;
        this.likePostService = likePostService;
        this.userRepository = userRepository;
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

    @GetMapping("/{postId}/liked-by-me")
    public Map<String, Boolean> likedByMe(@PathVariable Long postId,
                                          Authentication authentication) {
        // ★ 여기! findByUsername 사용
        User me = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        boolean liked = likePostRepository.existsByPost_IdAndUser_Id(postId, me.getId());
        return Map.of("likedByMe", liked);
    }


}
