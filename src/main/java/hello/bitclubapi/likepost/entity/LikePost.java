package hello.bitclubapi.likepost.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "like_post")
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;     // 게시글 ID (연관관계 매핑 전이라면 이렇게)
    private Long userId;     // 유저 ID

    private LocalDateTime likedAt;

    // 기본 생성자 (JPA용)
    protected LikePost() {}

    // 생성자
    public LikePost(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
        this.likedAt = LocalDateTime.now();
    }

    // Getter
    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }
}
