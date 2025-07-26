package hello.bitclubapi.post.service;

import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    //생성자 주입
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //글 작성
    public Post create(Post post){
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdateAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // 전체 글 조회
    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public Optional<Post> update(Long id, Post updatedPost){
        return postRepository.findById(id).map(post->{
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUpdateAt(LocalDateTime.now());
            post.setAuthor(updatedPost.getAuthor());
            return postRepository.save(post);
        });
    }

    public void delete(Long id){
        postRepository.deleteById((id));
    }





}
