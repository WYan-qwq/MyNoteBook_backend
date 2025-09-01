package com.example.mynotebook.Share;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(nullable = false)
    private Integer sharingId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column
    private Integer preCommentId;           // 可空：为 null 代表一级评论

    @Column(nullable = false)
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        if (createTime == null) createTime = LocalDateTime.now();
    }

    // --- getters & setters ---
    public Integer getCommentId() { return commentId; }
    public void setCommentId(Integer commentId) { this.commentId = commentId; }

    public Integer getSharingId() { return sharingId; }
    public void setSharingId(Integer sharingId) { this.sharingId = sharingId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getPreCommentId() { return preCommentId; }
    public void setPreCommentId(Integer preCommentId) { this.preCommentId = preCommentId; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}