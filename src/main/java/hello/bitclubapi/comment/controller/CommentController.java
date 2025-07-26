package hello.bitclubapi.comment.controller;

import hello.bitclubapi.comment.entity.Comment;
import hello.bitclubapi.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Comment> create(@RequestBody Comment comment){
        return ResponseEntity.ok(commentService.create(comment));
    }

    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }



}
