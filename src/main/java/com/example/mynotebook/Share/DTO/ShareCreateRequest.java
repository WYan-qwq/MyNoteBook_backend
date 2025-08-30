package com.example.mynotebook.Share.DTO;

import java.time.LocalDate;

public class ShareCreateRequest {
    private Integer userId;
    private LocalDate planDate;
    private String title;
    private String details;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public LocalDate getPlanDate() { return planDate; }
    public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}