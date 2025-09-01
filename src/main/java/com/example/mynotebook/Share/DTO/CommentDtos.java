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
}