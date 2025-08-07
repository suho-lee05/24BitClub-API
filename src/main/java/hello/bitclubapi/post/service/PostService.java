package hello.bitclubapi.post.service;

import hello.bitclubapi.comment.entity.Comment;
import hello.bitclubapi.comment.repository.CommentRepository;
import hello.bitclubapi.commentedpost.repository.CommentedPostRepository;
import hello.bitclubapi.likepost.repository.LikePostRepository;
import hello.bitclubapi.post.dto.PostWithStats;
import hello.bitclubapi.user.entity.User;
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
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;
    private final CommentedPostRepository commentedPostRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, LikePostRepository likePostRepository, CommentRepository commentRepository, CommentedPostRepository commentedPostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likePostRepository = likePostRepository;
        this.commentRepository = commentRepository;
        this.commentedPostRepository = commentedPostRepository;
    }

    /** 모든 게시글 + 통계 한 번에 가져오기 */
    public List<PostWithStats> getAllPostsWithStats() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(p -> {
            PostWithStats dto = new PostWithStats();
            dto.setPostId(p.getId());
            dto.setTitle(p.getTitle());
            dto.setUserId(p.getUser().getId());
            dto.setUsername(p.getUser().getUsername());
            dto.setCreatedAt(p.getCreatedAt());
            dto.setLikeCount(likePostRepository.countByPost_Id(p.getId()));
            dto.setCommentCount(commentRepository.countByPost_Id(p.getId()));
            return dto;
        }).toList();
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
    @Transactional
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

    /** 제목으로 포스트 검색 (부분 일치)*/
    public List<Post> searchByTitle(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }

    /** 내가 쓴 댓글 post 찾기*/
    public List<PostWithStats> getPostsCommentedByUser(Long userId) {
        return commentRepository.findAllByUser_Id(userId).stream()
                // 1) Comment → Post 로 매핑 후 중복 제거
                .map(Comment::getPost)
                .distinct()
                .map(p -> {
                    PostWithStats dto = new PostWithStats();
                    dto.setPostId(p.getId());
                    dto.setTitle(p.getTitle());
                    dto.setUserId(p.getUser().getId());
                    dto.setUsername(p.getUser().getUsername());
                    dto.setCreatedAt(p.getCreatedAt());
                    // 2) Like 수 조회
                    dto.setLikeCount(likePostRepository.countByPost_Id(p.getId()));
                    // 3) Comment 수 조회 (CommentRepository의 countByPost_Id)
                    dto.setCommentCount(commentRepository.countByPost_Id(p.getId()));
                    return dto;
                })
                .toList();
    }

    /** 내가 좋아요 누른 post 찾기*/
    public List<PostWithStats> getPostsLikedByUser(Long userId) {
        return likePostRepository.findAllByUser_Id(userId).stream()
                .map(lp -> {
                    Post p = lp.getPost();
                    PostWithStats dto = new PostWithStats();
                    dto.setPostId(p.getId());
                    dto.setTitle(p.getTitle());
                    dto.setUserId(p.getUser().getId());
                    dto.setUsername(p.getUser().getUsername());
                    dto.setCreatedAt(p.getCreatedAt());
                    dto.setLikeCount(likePostRepository.countByPost_Id(p.getId()));
                    dto.setCommentCount(commentRepository.countByPost_Id(p.getId()));
                    return dto;
                })
                .toList();
    }


}
