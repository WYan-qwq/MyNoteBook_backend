package com.example.mynotebook.Share;

import com.example.mynotebook.Plan.PlanEntity;
import com.example.mynotebook.User.UserInfoEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "share")
public class ShareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SharingId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "PlanId", nullable = false)
    private PlanEntity plan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserInfoEntity user;

    @Column(name = "CreateTime", nullable = false)
    private Instant createTime;

    @Column(name = "Title", length = 255, nullable = false)
    private String title;

    @Column(name = "Details", length = 2000)
    private String details;

    @Column(name = "Likes", nullable = false/*, columnDefinition = "int default 0"*/)
    private Integer likes;

    @Column(name = "Comments", nullable = false/*, columnDefinition = "int default 0"*/)
    private Integer comments;

    @PrePersist
    public void prePersist() {
        if (createTime == null) createTime = Instant.now();
        if (likes == null) likes = 0;
        if (comments == null) comments = 0;
    }

    // getters & setters
    public Integer getId() { return id; }
    public PlanEntity getPlan() { return plan; }
    public void setPlan(PlanEntity plan) { this.plan = plan; }
    public UserInfoEntity getUser() { return user; }
    public void setUser(UserInfoEntity user) { this.user = user; }
    public Instant getCreateTime() { return createTime; }
    public void setCreateTime(Instant createTime) { this.createTime = createTime; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }
    public Integer getComments() { return comments; }
    public void setComments(Integer comments) { this.comments = comments; }
}