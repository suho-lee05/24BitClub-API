package hello.bitclubapi.likepost.entity;

import hello.bitclubapi.post.entity.Post;
import hello.bitclubapi.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_post")
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime likedAt;

    // 생성자
    public LikePost() {}

    public LikePost(Post post, User user) {
        this.post = post;
        this.user = user;
        this.likedAt = LocalDateTime.now();
    }

    // Getter
    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }
}
