package com.example.mynotebook.Plan.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class PlanUpdateRequest {

    private LocalDate date;

    @Min(0) @Max(23)
    private Integer hour;

    @Min(0) @Max(59)
    private Integer minute;

    private String title;
    private String details;

    /** 0/1 */
    private Integer alarm;

    /** 0/1 */
    private Integer finished;
}