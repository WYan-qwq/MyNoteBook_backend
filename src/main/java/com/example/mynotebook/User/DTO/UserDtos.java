package com.example.mynotebook.User.DTO;

import lombok.Data;

public class UserDtos {

    @Data
    public static class ProfileResponse {
        private Integer id;
        private String userName;
        private String picture;
        private String email;
    }

    @Data
    public static class UpdateProfileRequest {
        private String userName;  // 允许为空：不修改
        private String picture;   // 允许为空：不修改
    }

    @Data
    public static class ChangePasswordRequest {
        private String newPassword;
    }
}