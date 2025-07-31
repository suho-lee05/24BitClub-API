package hello.bitclubapi.likepost.service;

import hello.bitclubapi.likepost.entity.LikePost;
import hello.bitclubapi.likepost.repository.LikePostRepository;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.repository.PostRepository;
import hello.bitclubapi.user.entity.User;
import hello.bitclubapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikePostService(LikePostRepository likePostRepository,
                           PostRepository postRepository,
                           UserRepository userRepository) {
        this.likePostRepository = likePostRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (likePostRepository.findByPostAndUser(post, user).isPresent()) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        LikePost like = new LikePost(post, user);
        likePostRepository.save(like);
    }

    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        LikePost like = likePostRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));

        likePostRepository.delete(like);
    }

    public long getLikeCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return likePostRepository.countByPost(post);
    }
}
