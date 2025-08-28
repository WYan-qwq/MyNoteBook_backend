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

    /** 创建分享 */
    public ShareDtos.ShareItem create(ShareCreateRequest req) {
        if (req.getUserId() == null || req.getPlanId() == null
                || req.getTitle() == null || req.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId/planId/title required");
        }

        UserInfoEntity user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        PlanEntity plan = planRepo.findById(req.getPlanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));

        ShareEntity e = new ShareEntity();
        e.setUser(user);
        e.setPlan(plan);
        e.setTitle(req.getTitle().trim());
        e.setDetails(req.getDetails());
        e.setCreateTime(Instant.now());
        e.setLikes(0);
        e.setComments(0);

        ShareEntity saved = shareRepo.save(e);

        return new ShareDtos.ShareItem(
                saved.getId(),
                saved.getPlan().getId(),
                // 如果你的 UserInfoEntity 没有 getUserId()，可改成 getId()
                saved.getUser().getId(),
                saved.getTitle(),
                saved.getDetails(),
                saved.getCreateTime(),
                saved.getLikes(),
                saved.getComments()
        );
    }

    /** 列表查询：可选按 userId 过滤，按创建时间倒序（不分页） */
    public List<ShareDtos.ShareView> list(Integer userId) {
        List<ShareEntity> rows = (userId == null)
                ? shareRepo.findAllByOrderByCreateTimeDesc()
                : shareRepo.findByUser_IdOrderByCreateTimeDesc(userId);

        return rows.stream().map(this::toView).toList();
    }

    /** Entity -> 视图 DTO */
    private ShareDtos.ShareView toView(ShareEntity e) {
        UserInfoEntity u = e.getUser();
        PlanEntity pl = e.getPlan();

        ShareDtos.Author author = new ShareDtos.Author(
                u.getId(),          // 确保有 getUserId()/或换成 getId()
                u.getUserName(),        // 确保有 getUserName()
                u.getPicture()          // 确保有 getPicture()
        );

        ShareDtos.ShareContent share = new ShareDtos.ShareContent(
                e.getTitle(),
                e.getDetails()
        );

        ShareDtos.PlanBrief plan = new ShareDtos.PlanBrief(
                pl.getId(),
                pl.getDate(),           // 如果 PlanEntity 的 date 是 Instant，则把 DTO 改成 Instant
                pl.getHour(),
                pl.getMinute(),
                pl.getTitle(),
                pl.getDetails(),
                pl.getAlarm(),
                pl.getFinished()
        );

        return new ShareDtos.ShareView(
                e.getId(),
                e.getCreateTime(),
                e.getLikes(),
                e.getComments(),
                author, share, plan
        );
    }
}