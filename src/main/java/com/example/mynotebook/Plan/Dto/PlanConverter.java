package com.example.mynotebook.Plan.Dto;

import com.example.mynotebook.Plan.PlanEntity;

public class PlanConverter {
    public static PlanDto toDto(PlanEntity e) {
        PlanDto dto = new PlanDto();
        dto.setId(e.getId());
        dto.setCreateTime(e.getCreateTime());
        dto.setDate(e.getDate());
        dto.setHour(e.getHour());
        dto.setMinute(e.getMinute());
        dto.setTitle(e.getTitle());
        dto.setDetails(e.getDetails());
        dto.setAlarm(e.getAlarm());
        dto.setFinished(e.getFinished());
        return dto;
    }
}
