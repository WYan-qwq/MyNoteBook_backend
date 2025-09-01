package com.example.mynotebook.Share;

import com.example.mynotebook.Share.DTO.ShareCreateRequest;
import com.example.mynotebook.Share.DTO.ShareDtos;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/share")
@CrossOrigin // 如需要前端跨域
public class ShareController {

    private final ShareService service;

    public ShareController(ShareService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ShareDtos.ShareItem create(@RequestBody ShareCreateRequest req) {
        return service.create(req);
    }

    /** 获取所有 share（或某用户的 share） */
    @GetMapping("/list")
    public List<ShareDtos.ShareView> list(@RequestParam(required = false) Integer userId) {
        return service.list(userId);
    }
    @PostMapping("/like")
    public ShareDtos.LikeResult like(@RequestBody ShareDtos.LikeCreateRequest req) {
        return service.like(req);
    }

    @GetMapping("/liked")
    public Map<String, Object> liked(
            @RequestParam Integer userId,
            @RequestParam Integer shareId
    ) {
        boolean liked = service.hasLiked(userId, shareId);   // ✅ 实例调用
        return Map.of(
                "userId", userId,
                "shareId", shareId,
                "liked", liked
        );
    }
}