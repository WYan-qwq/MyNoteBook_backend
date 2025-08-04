package com.example.mynotebook.Plan;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    public List<PlanEntity> getPlansByUserId(Integer userId) {
        return planRepository.findByUser_Id(userId);
    }
}