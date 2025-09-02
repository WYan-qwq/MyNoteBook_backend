package com.example.mynotebook.Share;

import com.example.mynotebook.Share.DTO.CommentDtos;
import com.example.mynotebook.Share.DTO.ShareCreateRequest;
import com.example.mynotebook.Share.DTO.ShareDtos;
import com.example.mynotebook.Share.DTO.ShareUpdateDtos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/share")
@CrossOrigin // 如需要前端跨域
public class ShareController {

    private final ShareService service;
    private final CommentService commentService;

    public ShareController(ShareService service,CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
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

    @GetMapping("/{id}/comments")
    public List<CommentDtos.CommentView> listComments(@PathVariable("id") Integer sharingId) {
        return commentService.listByShare(sharingId);
    }

    @PostMapping("/{id}/addcomments")
    public CommentDtos.CommentView createComment(@PathVariable("id") Integer sharingId,
                                                 @RequestBody CommentDtos.CommentCreateRequest req) {
        req.setSharingId(sharingId);   // 用 path 上的 id
        return commentService.create(req);
    }

    @DeleteMapping("/deletecomments/{commentId}")
    public Map<String, Object> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteOne(commentId);
        return Map.of("ok", true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShareEntity> editShare(
            @PathVariable("id") Integer id,
            @RequestBody ShareUpdateDtos.UpdateRequest req
    ) {
        ShareEntity updated = service.editShare(id, req);
        return ResponseEntity.ok(updated);
    }

    /** 删除分享：连带删除点赞/评论 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable("id") Integer id) {
        service.deleteShare(id);
        return ResponseEntity.noContent().build();
    }

    /** 按点赞数降序排列；可传 limit */
    @GetMapping("/popular")
    public ResponseEntity<List<ShareEntity>> popular(@RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(service.listPopular(limit));
    }

}