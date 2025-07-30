package hello.bitclubapi.commentedpost.service;

import hello.bitclubapi.commentedpost.entity.CommentedPost;
import hello.bitclubapi.commentedpost.repository.CommentedPostRepository;
import hello.bitclubapi.fold.entity.User;
import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.post.repository.PostRepository;
import hello.bitclubapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentedPostService {

    private final CommentedPostRepository commentedPostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentedPostService(CommentedPostRepository commentedPostRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentedPostRepository = commentedPostRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //단일 조회
    public CommentedPost getOne(Long userId, Long postId) {
        return commentedPostRepository.findByUser_IdAndPost_Id(userId,postId)
                .orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));

    }

    //사용자별 목록
    public List<CommentedPost> listByUser(Long userId) {
        return commentedPostRepository.findAllByUser_Id(userId);
    }

    //생성 또는 업데이트
    @Transactional
    public CommentedPost upsert(Long userId, Long postId, Integer myCnt, Integer totalCnt, LocalDateTime lastAt){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("User 없음"));
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("Post 없음"));
        CommentedPost commentedPost = commentedPostRepository.findByUser_IdAndPost_Id(userId, postId)
                .orElseGet(CommentedPost::new);

        commentedPost.setUser(user);
        commentedPost.setPost(post);
        commentedPost.setMyCommentCount(myCnt);
        commentedPost.setTotalCommentCount(totalCnt);
        commentedPost.setLastCommentedAt(lastAt);

        return commentedPostRepository.save(commentedPost);

    }

    //삭제

    public void delete(Long commentedPostId) {
        commentedPostRepository.deleteById(commentedPostId);
    }



}
