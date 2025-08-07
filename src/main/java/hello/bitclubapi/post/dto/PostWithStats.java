package hello.bitclubapi.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostWithStats {
    private Long postId;
    private String title;
    private Long userId;
    private String username;

    @Column(nullable = false, updatable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private long likeCount;
    private long commentCount;

    public PostWithStats() {
    }

    public PostWithStats(Long postId, String title, Long userId, String username, LocalDateTime createdAt, long likeCount, long commentCount) {
        this.postId = postId;
        this.title = title;
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
