package com.example.mynotebook.Plan;


import com.example.mynotebook.Plan.DTO.PlanDtos;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanService {
    private final PlanRepository repo;
    public PlanService(PlanRepository repo) { this.repo = repo; }

    public List<PlanDtos.PlanItem> getPlansForDay(Integer userId, LocalDate date) {
        return repo.findByUser_IdAndDateOrderByHourAscMinuteAscIdAsc(userId, date)
                .stream()
                .map(p -> new PlanDtos.PlanItem(
                        p.getId(), p.getDate(), p.getHour(), p.getMinute(),
                        p.getTitle(), p.getDetails(), p.getAlarm(), p.getFinished()
                ))
                .toList();
    }
}