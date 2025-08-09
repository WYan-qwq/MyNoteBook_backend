package com.example.mynotebook.Plan.DTO;

import java.time.Instant;

public class PlanDto {
    private Integer id;
    private Instant createTime;
    private Instant date;
    private Integer hour;
    private Integer minute;
    private String title;
    private String details;
    private Integer alarm;
    private Integer finished;

    public PlanDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "PlanDto{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", date=" + date +
                ", hour=" + hour +
                ", minute=" + minute +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", alarm=" + alarm +
                ", finished=" + finished +
                '}';
    }
}