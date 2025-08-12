package com.example.mynotebook.Plan;

import com.example.mynotebook.User.UserInfoEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Table(name = "plan")
@Entity
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserInfoEntity user;

    @Column(name = "CreateTime")
    private Instant createTime;

    @Column(name = "Date", nullable = false) // SQL 列类型应为 DATE
    private java.time.LocalDate date;

    @Column(name = "Hour")
    private Integer hour;

    @Column(name = "Minute")
    private Integer minute;

    @Column(name = "Title", length = 100)
    private String title;

    @Column(name = "Details", length = 100)
    private String details;

    @Column(name = "Alarm")
    private Integer alarm;

    @Column(name = "Finished")
    private Integer finished;

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public UserInfoEntity getUser() {
        return user;
    }

    public void setUser(UserInfoEntity user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
