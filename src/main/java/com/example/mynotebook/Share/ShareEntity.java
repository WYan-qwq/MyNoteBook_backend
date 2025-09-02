package com.example.mynotebook.Share;

import com.example.mynotebook.User.UserInfoEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "share")
public class ShareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private UserInfoEntity user;

    // ✅ 用日期替代 Plan 外键
    @Column(name = "plan_date", nullable = false)
    private LocalDate planDate;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "details", length = 500)
    private String details;

    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @Column(name = "likes", nullable = false)
    private Integer likes = 0;

    @Column(name = "comments", nullable = false)
    private Integer comments = 0;

    // --- getters/setters ---
    public Integer getId() { return id; }
    public UserInfoEntity getUser() { return user; }
    public void setUser(UserInfoEntity user) { this.user = user; }

    public LocalDate getPlanDate() { return planDate; }
    public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Instant getCreateTime() { return createTime; }
    public void setCreateTime(Instant createTime) { this.createTime = createTime; }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public Integer getComments() { return comments; }
    public void setComments(Integer comments) { this.comments = comments; }
}