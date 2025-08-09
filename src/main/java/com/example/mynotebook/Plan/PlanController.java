package com.example.mynotebook.Plan;


import com.example.mynotebook.Plan.DTO.PlanConverter;
import com.example.mynotebook.Plan.DTO.PlanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    /**
     * GET /api/plans/user/{userId}
     *
     * 返回该 userId 下所有的计划，自动序列化为 JSON 数组
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlanDto>> getPlansByUserId(@PathVariable Integer userId) {
        List<PlanEntity> entities = planService.getPlansByUserId(userId);
        List<PlanDto> dtos = entities.stream()
                .map(PlanConverter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}