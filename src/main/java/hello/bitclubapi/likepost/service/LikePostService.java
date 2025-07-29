package hello.bitclubapi.likepost.service;

import hello.bitclubapi.likepost.entity.LikePost;
import hello.bitclubapi.likepost.repository.LikePostRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LikePostService {

    private final LikePostRepository likePostRepository;

    public LikePostService(LikePostRepository likePostRepository) {
        this.likePostRepository = likePostRepository;
    }

    // 좋아요 누르기
    public void likePost(Long postId, Long userId) {
        Optional<LikePost> existing = likePostRepository.findByPostIdAndUserId(postId, userId);
        if (existing.isEmpty()) {
            likePostRepository.save(new LikePost(postId, userId));
        }
    }

    // 좋아요 취소
    public void unlikePost(Long postId, Long userId) {
        Optional<LikePost> existing = likePostRepository.findByPostIdAndUserId(postId, userId);
        existing.ifPresent(likePostRepository::delete);
    }

    // 좋아요 수 조회
    public int getLikeCount(Long postId) {
        return likePostRepository.countByPostId(postId);
    }
}
