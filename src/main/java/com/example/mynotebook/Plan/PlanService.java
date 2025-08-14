package com.example.mynotebook.Plan;


import com.example.mynotebook.Plan.DTO.PlanCreateRequest;
import com.example.mynotebook.Plan.DTO.PlanDtos;
import com.example.mynotebook.User.UserInfoEntity;
import com.example.mynotebook.User.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanService {
    private final PlanRepository repo;
    private final UserInfoRepository userRepo;
    public PlanService(PlanRepository repo, UserInfoRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public List<PlanDtos.PlanItem> getPlansForDay(Integer userId, LocalDate date) {
        return repo.findByUser_IdAndDateOrderByHourAscMinuteAscIdAsc(userId, date)
                .stream()
                .map(p -> new PlanDtos.PlanItem(
                        p.getId(), p.getDate(), p.getHour(), p.getMinute(),
                        p.getTitle(), p.getDetails(), p.getAlarm(), p.getFinished(),p.getCreateTime()
                ))
                .toList();
    }
    public PlanDtos.PlanItem create(PlanCreateRequest req) {
        // 通过 id 绑定用户（懒加载引用，不额外查库）
        UserInfoEntity userRef = userRepo.getReferenceById(req.getUserId());

        PlanEntity p = new PlanEntity();
        p.setUser(userRef);
        p.setCreateTime(req.getCreateTime());   // 前端传来的手机系统时间
        p.setDate(req.getDate());               // 你的实体是 LocalDate，就直接存
        p.setHour(req.getHour());
        p.setMinute(req.getMinute());
        p.setTitle(req.getTitle());
        p.setDetails(req.getDetails());
        p.setAlarm(req.getAlarm());
        p.setFinished(req.getFinished());

        PlanEntity saved = repo.save(p);

        // 返回与你现有列表一致的 DTO 字段（和 /day 查询保持同构）
        return new PlanDtos.PlanItem(
                saved.getId(),
                saved.getDate(),
                saved.getHour(),
                saved.getMinute(),
                saved.getTitle(),
                saved.getDetails(),
                saved.getAlarm(),
                saved.getFinished(),
                saved.getCreateTime()
        );
    }

}