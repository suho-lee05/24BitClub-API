package hello.bitclubapi.commentedpost.entity;

import hello.bitclubapi.fold.entity.User;
import hello.bitclubapi.post.entity.Post;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commented_post")
public class CommentedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commmentedPostId;

    // CommentedPost 여러 개가 하나의 User에 속한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  //댓글 작성자(나)

    //CommentedPost 여러 개가 하나의 Post에 속한다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;  //댓글을 단 게시글

    private Integer myCommentCount = 0;
    private Integer totalCommentCount = 0;

    private LocalDateTime lastCommentedAt;

    public Long getCommmentedPostId() {
        return commmentedPostId;
    }

    public void setCommmentedPostId(Long commmentedPostId) {
        this.commmentedPostId = commmentedPostId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getMyCommentCount() {
        return myCommentCount;
    }

    public void setMyCommentCount(Integer myCommentCount) {
        this.myCommentCount = myCommentCount;
    }

    public Integer getTotalCommentCount() {
        return totalCommentCount;
    }

    public void setTotalCommentCount(Integer totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public LocalDateTime getLastCommentedAt() {
        return lastCommentedAt;
    }

    public void setLastCommentedAt(LocalDateTime lastCommentedAt) {
        this.lastCommentedAt = lastCommentedAt;
    }
}
