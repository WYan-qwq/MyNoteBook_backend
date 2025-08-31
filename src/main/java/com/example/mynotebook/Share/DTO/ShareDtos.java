package com.example.mynotebook.Share.DTO;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ShareDtos {

    /** 新建分享后的简单返回 */
    public static class ShareItem {
        public Integer sharingId;
        public Integer userId;
        public String title;
        public String details;
        public Instant createTime;
        public Integer likes;
        public Integer comments;
        public LocalDate planDate;       // ✅
        public java.util.List<PlanBrief> plans;

        public ShareItem(Integer sharingId, Integer userId,
                         String title, String details, Instant createTime,
                         Integer likes, Integer comments,LocalDate planDate, List<PlanBrief> plans) {
            this.sharingId = sharingId;
            this.userId = userId;
            this.title = title;
            this.details = details;
            this.createTime = createTime;
            this.likes = likes;
            this.comments = comments;
            this.planDate = planDate;
            this.plans = plans;
        }
    }

    /** 列表返回的单条视图 */
    public static class ShareView {
        public Integer sharingId;
        public Instant createTime;
        public Integer likes;
        public Integer comments;
        public Author author;        // 作者信息
        public ShareContent share;   // 分享文案（与计划独立）
        public LocalDate planDate;      // ✅ 分享的那一天
        public List<PlanBrief> plans;       // 被分享的计划

        public ShareView(Integer sharingId, Instant createTime, Integer likes, Integer comments,
                         Author author, ShareContent share, LocalDate planDate, List<PlanBrief> plans) {
            this.sharingId = sharingId;
            this.createTime = createTime;
            this.likes = likes;
            this.comments = comments;
            this.author = author;
            this.share = share;
            this.planDate = planDate;
            this.plans = plans;
        }
    }

    /** 作者信息（来自 user_info） */
    public static class Author {
        public Integer userId;
        public String userName;
        public String picture;

        public Author(Integer userId, String userName, String picture) {
            this.userId = userId;
            this.userName = userName;
            this.picture = picture;
        }
    }

    /** 分享卡片自身的文案 */
    public static class ShareContent {
        public String title;
        public String details;

        public ShareContent(String title, String details) {
            this.title = title;
            this.details = details;
        }
    }

    /** 被分享的计划简要信息（按你的 PlanEntity 字段调整） */
    public static class PlanBrief {
        public Integer id;
        public LocalDate date;   // 若 PlanEntity 为 LocalDate，则把这里改成 LocalDate
        public Integer hour;
        public Integer minute;
        public String title;
        public String details;
        public Integer alarm;
        public Integer finished;

        public PlanBrief(Integer id, LocalDate date, Integer hour, Integer minute,
                         String title, String details, Integer alarm, Integer finished) {
            this.id = id;
            this.date = date;
            this.hour = hour;
            this.minute = minute;
            this.title = title;
            this.details = details;
            this.alarm = alarm;
            this.finished = finished;
        }
    }

    public static class LikeCreateRequest {
        private Integer shareId;
        private Integer userId;

        public Integer getShareId() { return shareId; }
        public void setShareId(Integer shareId) { this.shareId = shareId; }

        public Integer getUserId() { return userId; }
        public void setUserId(Integer userId) { this.userId = userId; }
    }

    public static class LikeResult {
        public Integer shareId;
        public Integer likes;     // 最新点赞数
        public boolean duplicated; // 是否重复点赞（已点过）

        public LikeResult(Integer shareId, Integer likes, boolean duplicated) {
            this.shareId = shareId;
            this.likes = likes;
            this.duplicated = duplicated;
        }
    }

    public static class UnlikeResult {
        public Integer shareId;
        public Integer likes;     // 最新点赞数
        public boolean removed;   // 是否真的删除了这次点赞（若本就没点过，则为 false）

        public UnlikeResult(Integer shareId, Integer likes, boolean removed) {
            this.shareId = shareId;
            this.likes = likes;
            this.removed = removed;
        }
    }
}