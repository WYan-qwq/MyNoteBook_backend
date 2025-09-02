package com.example.mynotebook.Share.DTO;

import lombok.Data;

@Data
public class ShareUpdateDtos {
    @Data
    public static class UpdateRequest {
        private String title;    // 允许为 null：不改
        private String details;  // 允许为 null：不改
    }
}