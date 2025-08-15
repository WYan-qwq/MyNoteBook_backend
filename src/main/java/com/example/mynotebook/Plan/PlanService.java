package com.example.mynotebook.Plan;


import com.example.mynotebook.Plan.DTO.PlanCreateRequest;
import com.example.mynotebook.Plan.DTO.PlanDtos;
import com.example.mynotebook.Plan.DTO.PlanUpdateRequest;
import com.example.mynotebook.User.UserInfoEntity;
import com.example.mynotebook.User.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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


    public List<PlanDtos.PlanItem> getPlansForWeek(Integer userId, LocalDate anyDateInWeek) {
        LocalDate start = anyDateInWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end   = anyDateInWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return repo.findByUser_IdAndDateBetweenOrderByDateAscHourAscMinuteAscIdAsc(userId, start, end)
                .stream()
                // 如果你有 PlanConverter.toItem(entity) 就改成那个
                .map(e -> new PlanDtos.PlanItem(
                        e.getId(),
                        e.getDate(),     // LocalDate
                        e.getHour(),
                        e.getMinute(),
                        e.getTitle(),
                        e.getDetails(),
                        e.getAlarm(),
                        e.getFinished(),
                        e.getCreateTime()
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

    @Transactional
    public PlanDtos.PlanItem update(Integer id, PlanUpdateRequest req) {
        PlanEntity p = repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found: " + id)
        );

        if (req.getDate() != null)       p.setDate(req.getDate());
        if (req.getHour() != null)       p.setHour(req.getHour());
        if (req.getMinute() != null)     p.setMinute(req.getMinute());
        if (req.getTitle() != null)      p.setTitle(req.getTitle());
        if (req.getDetails() != null)    p.setDetails(req.getDetails());
        if (req.getAlarm() != null)      p.setAlarm(req.getAlarm());
        if (req.getFinished() != null)   p.setFinished(req.getFinished());

        PlanEntity saved = repo.save(p);

        // 映射成你项目里使用的返回 DTO（按你现有 PlanDtos.PlanItem 字段来）
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

    @Transactional
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found: " + id);
        }
        repo.deleteById(id);
    }
}
