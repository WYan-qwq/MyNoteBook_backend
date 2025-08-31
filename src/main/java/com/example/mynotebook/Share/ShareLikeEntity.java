package com.example.mynotebook.Share;

import com.example.mynotebook.User.UserInfoEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "share_like",
        uniqueConstraints = @UniqueConstraint(name = "uk_share_user", columnNames = {"share_id", "user_id"})
)
public class ShareLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "share_id", nullable = false)
    private ShareEntity share;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfoEntity user;

    @Column(name = "create_time", nullable = false, updatable = false)
    private Instant createTime;

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ShareEntity getShare() { return share; }
    public void setShare(ShareEntity share) { this.share = share; }

    public UserInfoEntity getUser() { return user; }
    public void setUser(UserInfoEntity user) { this.user = user; }

    public Instant getCreateTime() { return createTime; }
    public void setCreateTime(Instant createTime) { this.createTime = createTime; }
}