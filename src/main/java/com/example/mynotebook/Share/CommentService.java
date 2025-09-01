package com.example.mynotebook.Share;

import com.example.mynotebook.Share.DTO.CommentDtos;
import com.example.mynotebook.Share.DTO.CommentDtos.CommentView;
import com.example.mynotebook.User.UserInfoEntity;
import com.example.mynotebook.User.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repo;
    private final ShareRepository shareRepo;
    private final UserInfoRepository userRepo;   // ✅ 注入用户仓库

    /** 返回树形结构（一级评论 + 子评论，时间升序） */
    public List<CommentView> listByShare(Integer sharingId) {
        // 1) 读出该分享下所有评论（时间升序）
        List<CommentEntity> all = repo.findBySharingIdOrderByCreateTimeAsc(sharingId);

        // 2) 批量读取这些评论关联的所有用户，避免 N+1
        Set<Integer> uids = all.stream()
                .map(CommentEntity::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Integer, UserInfoEntity> users = userRepo.findAllById(uids).stream()
                .collect(Collectors.toMap(UserInfoEntity::getId, Function.identity()));

        // 3) 先把每条实体转成 view，放到一个 map 里备用
        Map<Integer, CommentView> map = new LinkedHashMap<>();
        for (CommentEntity e : all) {
            map.put(e.getCommentId(), toView(e, users));
        }

        // 4) 再组装父子
        List<CommentView> roots = new ArrayList<>();
        for (CommentEntity e : all) {
            CommentView v = map.get(e.getCommentId());
            Integer pid = e.getPreCommentId();
            if (pid == null) {
                roots.add(v);
            } else {
                CommentView p = map.get(pid);
                if (p != null) p.getChildren().add(v);
                else roots.add(v); // 兜底：父丢失则当作根
            }
        }
        return roots;
    }

    /** 把实体转为带作者信息的视图对象 */
    private CommentView toView(CommentEntity e, Map<Integer, UserInfoEntity> users) {
        CommentView v = new CommentView();
        v.setCommentId(e.getCommentId());
        v.setSharingId(e.getSharingId());
        v.setContent(e.getContent());
        v.setPreCommentId(e.getPreCommentId());
        v.setCreateTime(e.getCreateTime() == null ? null : e.getCreateTime().toString());

        // ✅ 组装作者信息
        CommentDtos.AuthorBrief a = new CommentDtos.AuthorBrief();
        a.setUserId(e.getUserId());
        UserInfoEntity u = users.get(e.getUserId());
        if (u != null) {
            // userName 兜底：为空时用 "user{id}"
            String name = (u.getUserName() != null && !u.getUserName().isBlank())
                    ? u.getUserName()
                    : ("user" + u.getId());
            a.setUserName(name);
            a.setPicture(u.getPicture()); // 可以为 null，前端再用占位图
        } else {
            a.setUserName("user" + e.getUserId());
            a.setPicture(null);
        }
        v.setAuthor(a);

        return v;
    }
    @Transactional
    public CommentView create(CommentDtos.CommentCreateRequest req) {
        Integer parentId = req.getPreCommentId();

        if (req.getUserId() == null || req.getSharingId() == null) {
            throw new IllegalArgumentException("userId and sharingId are required.");
        }
        if (req.getContent() == null || req.getContent().isBlank()) {
            throw new IllegalArgumentException("content is required.");
        }

        // 校验 share 存在
        ShareEntity share = shareRepo.findById(req.getSharingId())
                .orElseThrow(() -> new IllegalArgumentException("Share not found: " + req.getSharingId()));

        // 如有 preCommentId，校验它属于同一分享
        if (req.getPreCommentId() != null) {
            CommentEntity parent = repo.findById(req.getPreCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found: " + req.getPreCommentId()));
            if (!parent.getSharingId().equals(req.getSharingId())) {
                throw new IllegalArgumentException("preCommentId not under the same sharing.");
            }
        }

        // 解析时间：前端未传则用 now()
        LocalDateTime when = LocalDateTime.now();
        if (req.getCreateTime() != null && !req.getCreateTime().isBlank()) {
            try {
                when = LocalDateTime.parse(req.getCreateTime());
            } catch (DateTimeParseException e) {
                // 容错：也可以抛出异常
                when = LocalDateTime.now();
            }
        }

        // 构造并保存评论
        CommentEntity e = new CommentEntity();
        e.setSharingId(req.getSharingId());
        e.setUserId(req.getUserId());
        e.setContent(req.getContent().trim());
        e.setPreCommentId(req.getPreCommentId());
        e.setCreateTime(when);

        e = repo.save(e); // commentId 自增由数据库完成（建议 @GeneratedValue）

        // comments +1
        shareRepo.incComments(req.getSharingId());

        // 返回带 author 的视图对象
        UserInfoEntity u = userRepo.findById(req.getUserId()).orElse(null);
        Map<Integer, UserInfoEntity> users = Map.of(req.getUserId(), u);
        return toView(e, users); // 复用你之前的 toView(CommentEntity, Map<Integer,UserInfoEntity>)
    }
}