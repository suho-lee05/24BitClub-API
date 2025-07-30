package hello.bitclubapi.post.service;

import hello.bitclubapi.fold.entity.User;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.repository.PostRepository;
import hello.bitclubapi.user.repository.UserRepository;
import hello.bitclubapi.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**전체 게시글 조회*/
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /** 특정 게시글 조회*/
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));
    }

    /** 특정 사용자 게시글 목록 */
    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findAllByUser_Id(userId);
    }

    /** 게시글 작성*/
    @Transactional
    public Post createPost(Long userId, String title, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);

    }

    /** 게시글 수정*/
    public Post updatePost(Long postId, Long userId, String newTitle, String newContent) {
        Post post = getPostById(postId);
        if(post.getUser().getId() != userId){
            throw new SecurityException("권환이 없습니다.");
        }
        post.setTitle(newTitle);
        post.setContent(newContent);
        return post;
    }

    /**게시글 삭제*/
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = getPostById(postId);
        if(post.getUser().getId() != userId){
            throw new SecurityException("<UNK> <UNK>.");

        }
        postRepository.delete(post);
    }

}
