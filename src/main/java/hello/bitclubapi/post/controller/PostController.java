package hello.bitclubapi.post.controller;

import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Post> create(@RequestBody Post post){
        Post created = postService.create(post);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Post>>  getAll(){
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Post> getById(@PathVariable Long id){
        return postService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post){
        return postService.update(id, post)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.ok().build();
    }



}
