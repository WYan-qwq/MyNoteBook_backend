package com.example.mynotebook.Share.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class CommentDtos {

    @Data
    public static class AuthorBrief {
        private Integer userId;
        private String userName;
        private String picture;
    }

    @Data
    public static class CommentView {
        private Integer commentId;
        private Integer sharingId;

        // ✅ 新增：作者信息（像 Share 一样）
        private AuthorBrief author;

        private String  content;
        private String  createTime;    // 用 ISO-8601 字符串，前端使用更方便
        private Integer preCommentId;

        // 子评论
        private List<CommentView> children = new ArrayList<>();
    }
    @Data
    public static class CommentCreateRequest {
        private Integer userId;         // 谁评论
        private Integer sharingId;      // 评论哪条分享（也可用 PathVariable 传）
        private String  content;        // 评论内容
        private String  createTime;     // 可选：ISO 字符串；不传则后端用 now()
        private Integer preCommentId;   // 可选：回复某条评论
    }
}