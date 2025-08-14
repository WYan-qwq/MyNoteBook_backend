package com.example.mynotebook.Plan.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;

/** 新建计划：前端传除 planId 外的所有字段 */
@Data
public class PlanCreateRequest {

    @NotNull
    private Integer userId;

    /** 手机系统时间（ISO-8601），例如 2025-08-14T02:10:00Z */
    @NotNull
    private Instant createTime;

    /** 当天的日历日（YYYY-MM-DD） */
    @NotNull
    private LocalDate date;

    @NotNull @Min(0) @Max(23)
    private Integer hour;

    @NotNull @Min(0) @Max(59)
    private Integer minute;

    @Size(max = 100)
    private String title;

    @Size(max = 100)
    private String details;

    /** 0/1 */
    private Integer alarm;

    /** 0/1 */
    private Integer finished;
}