package com.example.mynotebook.Share;

import com.example.mynotebook.Share.DTO.ShareCreateRequest;
import com.example.mynotebook.Share.DTO.ShareDtos;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/unlike")
    public ShareDtos.UnlikeResult unlike(@RequestBody ShareDtos.LikeCreateRequest req) {
        return service.unlike(req);
    }
}