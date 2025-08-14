package com.example.mynotebook.Plan;

import com.example.mynotebook.Plan.DTO.PlanCreateRequest;

import com.example.mynotebook.Plan.DTO.PlanDtos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Plan", description = "plan related")
@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService service;
    public PlanController(PlanService service) { this.service = service; }

    @Operation(summary = "today's plan", description = "show today's plan")
    @GetMapping("/day")
    public ResponseEntity<List<PlanDtos.PlanItem>> getPlansForDay(
            @RequestParam Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(service.getPlansForDay(userId, date));
    }


    @Operation(summary = "Create a plan", description = "Create a new plan with client-side createTime; date is a calendar day (LocalDate).")
    @PostMapping
    public ResponseEntity<PlanDtos.PlanItem> create(@Valid @RequestBody PlanCreateRequest req) {
        PlanDtos.PlanItem created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}