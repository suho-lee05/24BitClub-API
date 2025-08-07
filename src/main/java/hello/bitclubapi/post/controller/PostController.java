package hello.bitclubapi.post.controller;

import hello.bitclubapi.post.dto.PostWithStats;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

//    /** 1) 전체 게시글 조회*/
//    @GetMapping
//    public List<Post> getAllPosts() {
//        return postService.getAllPosts();
//    }

    /** 2) 특정 게시글 조회 */
    @GetMapping("/{postId}")
    public Post getOne(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    /** 3) 사용자별 게시글 조회 */
    @GetMapping("/user/{userId}")
    public List<Post> listByUser(@PathVariable Long userId) {
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
    public Post update(@PathVariable Long postId,
                       @RequestParam Long userId,
                       @RequestParam String title,
                       @RequestBody String content) {
        return postService.updatePost(postId, userId, title, content);
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
    public List<Post> search(@RequestParam("title") String title){
        return postService.searchByTitle(title);
    }

    /** 기존 listAll 대신, 메타까지 포함된 리스트를 반환 */
    @GetMapping
    public List<PostWithStats> listAllWithStats() {
        return postService.getAllPostsWithStats();
    }

}
