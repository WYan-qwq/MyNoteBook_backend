package com.example.mynotebook.Share;

import com.example.mynotebook.Plan.PlanEntity;
import com.example.mynotebook.Plan.PlanRepository;
import com.example.mynotebook.Share.DTO.ShareCreateRequest;
import com.example.mynotebook.Share.DTO.ShareDtos;
import com.example.mynotebook.User.UserInfoEntity;
import com.example.mynotebook.User.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class ShareService {

    private final ShareRepository shareRepo;
    private final PlanRepository planRepo;
    private final UserInfoRepository userRepo;

    public ShareService(ShareRepository shareRepo,
                        PlanRepository planRepo,
                        UserInfoRepository userRepo) {
        this.shareRepo = shareRepo;
        this.planRepo = planRepo;
        this.userRepo = userRepo;
    }

    /** 创建分享（按某一天，而不是单条 plan） */
    public ShareDtos.ShareItem create(ShareCreateRequest req) {
        if (req.getUserId() == null || req.getPlanDate() == null
                || req.getTitle() == null || req.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId/planDate/title required");
        }

        UserInfoEntity user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        ShareEntity e = new ShareEntity();
        e.setUser(user);
        e.setPlanDate(req.getPlanDate());     // ✅ 分享这一天
        e.setTitle(req.getTitle().trim());
        e.setDetails(req.getDetails());
        e.setCreateTime(Instant.now());
        e.setLikes(0);
        e.setComments(0);

        ShareEntity saved = shareRepo.save(e);
        List<PlanEntity> dayPlans =
                planRepo.findByUser_IdAndDateOrderByHourAscMinuteAsc(
                        saved.getUser().getId(), saved.getPlanDate());

        List<ShareDtos.PlanBrief> planBriefs = dayPlans.stream()
                .map(p -> new ShareDtos.PlanBrief(
                        p.getId(),
                        p.getDate(),          // 如果是 Instant 就相应改 DTO
                        p.getHour(),
                        p.getMinute(),
                        p.getTitle(),
                        p.getDetails(),
                        p.getAlarm(),
                        p.getFinished()
                ))
                .toList();
        return new ShareDtos.ShareItem(
                saved.getId(),
                saved.getUser().getId(),
                saved.getTitle(),
                saved.getDetails(),
                saved.getCreateTime(),
                saved.getLikes(),
                saved.getComments(),
                saved.getPlanDate(),
                planBriefs                   // ✅ 最后一行传当天的 PlanBrief 列表
        );
    }

    /** 列表查询：可选按 userId 过滤，按创建时间倒序（不分页） */
    public List<ShareDtos.ShareView> list(Integer userId) {
        List<ShareEntity> rows = (userId == null)
                ? shareRepo.findAllByOrderByCreateTimeDesc()
                : shareRepo.findByUser_IdOrderByCreateTimeDesc(userId);

        return rows.stream().map(this::toView).toList();
    }

    /** Entity -> 视图 DTO（附带当天全部计划列表） */
    private ShareDtos.ShareView toView(ShareEntity e) {
        UserInfoEntity u = e.getUser();

        // 拉取该用户这一天的所有计划，按时间升序
        List<PlanEntity> plans = planRepo.findByUser_IdAndDateOrderByHourAscMinuteAsc(
                u.getId(), e.getPlanDate()
        );

        List<ShareDtos.PlanBrief> planBriefs = plans.stream().map(p ->
                new ShareDtos.PlanBrief(
                        p.getId(),
                        p.getDate(),
                        p.getHour(),
                        p.getMinute(),
                        p.getTitle(),
                        p.getDetails(),
                        p.getAlarm(),
                        p.getFinished()
                )
        ).toList();

        ShareDtos.Author author = new ShareDtos.Author(
                u.getId(),
                u.getUserName(),
                u.getPicture()
        );

        ShareDtos.ShareContent content = new ShareDtos.ShareContent(
                e.getTitle(),
                e.getDetails()
        );

        return new ShareDtos.ShareView(
                e.getId(),
                e.getCreateTime(),
                e.getLikes(),
                e.getComments(),
                author,
                content,
                e.getPlanDate(),   // ✅ 哪一天
                planBriefs         // ✅ 这一天的所有 plan
        );
    }
}