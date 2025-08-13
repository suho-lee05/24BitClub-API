package hello.bitclubapi.post.controller;

import hello.bitclubapi.comment.repository.CommentRepository;
import hello.bitclubapi.likepost.repository.LikePostRepository;
import hello.bitclubapi.post.dto.PostDetail;
import hello.bitclubapi.post.dto.PostWithStats;
import hello.bitclubapi.post.dto.UpdatePostRequest;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;

    public PostController(PostService postService, LikePostRepository likePostRepository, CommentRepository commentRepository) {
        this.postService = postService;
        this.likePostRepository = likePostRepository;
        this.commentRepository = commentRepository;
    }

    //    /** 1) 전체 게시글 조회*/
//    @GetMapping
//    public List<Post> getAllPosts() {
//        return postService.getAllPosts();
//    }

    /** 2) 게시글 상세 조회 */
    @GetMapping("/{postId}")
    public PostDetail getOne(
            @PathVariable Long postId,
            @RequestHeader(value = "X-USER-ID", required = false) Long userId
    ) {
        return postService.getPostDetail(postId, userId);
    }

    /** 3) 내 게시물 조회 */
//    @GetMapping("/user/{userId}")
//    public List<Post> listByUser(@PathVariable Long userId) {
//        return postService.getPostsByUser(userId);
//    }
    /**
     * 내 게시물 조회
     */
    @GetMapping("/user/{userId}")
    public List<PostWithStats> listByUser(@PathVariable Long userId) {
        return postService.getPostsByUser(userId);
    }


    /** 4) 게시글 작성 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestParam Long userId,
                       @RequestParam String title,
                       @RequestBody String content) {
        return postService.createPost(userId, title, content);
    }

    /** 5) 게시글 수정 */
    @PutMapping("/{postId}")
    public PostDetail update(@PathVariable Long postId,
                             @RequestHeader("X-USER-ID") Long userId,
                             @RequestBody UpdatePostRequest req) {
        return postService.updatePost(postId, userId, req.title(), req.content());
    }

    /** 6) 게시글 삭제 */
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId,
                       @RequestParam Long userId) {
        postService.deletePost(postId, userId);
    }

    /** 7) 키워드 게시물 검색*/
    @GetMapping("/search")
    public Page<PostWithStats> search(
            @RequestParam("title") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        String[] parts = sort.split(",");
        var pageable = PageRequest.of(
                page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0])
        );
        return postService.searchByTitle(title, pageable);
    }



    /**
     * 메인 화면: 전체 게시글 + 통계 (페이징)
     * GET /api/posts?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public Page<PostWithStats> listAllWithStats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        String[] parts = sort.split(",");
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return postService.getAllPostsWithStats(pageable);
    }

    /**
     * 내가 댓글 단 모든 게시물 조회
     * GET /api/posts/commented/user/{userId}
     */
    @GetMapping("/commented/user/{userId}")
    public List<PostWithStats> getPostsCommentedByUser(@PathVariable Long userId) {
        return postService.getPostsCommentedByUser(userId);
    }

    /**
     * 내가 좋아요 누른 모든 게시물 조회
     * GET /api/posts/liked/user/{userId}
     */
    @GetMapping("/liked/user/{userId}")
    public List<PostWithStats> getPostsLikedByUser(@PathVariable Long userId) {
        return postService.getPostsLikedByUser(userId);
    }





}
