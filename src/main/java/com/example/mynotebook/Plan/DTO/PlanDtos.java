package com.example.mynotebook.Plan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

public final class PlanDtos {
    private PlanDtos() {}
    @Data
    @AllArgsConstructor
    public static class PlanItem {
        private Integer id;
        private LocalDate date;
        private Integer hour;
        private Integer minute;
        private String title;
        private String details;
        private Integer alarm;
        private Integer finished;
    }
}