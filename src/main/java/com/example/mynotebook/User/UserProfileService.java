package com.example.mynotebook.User;

import com.example.mynotebook.User.DTO.UserDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserInfoRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /* 查询个人资料 */
    @Transactional(readOnly = true)
    public UserDtos.ProfileResponse getProfile(Integer userId) {
        var u = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return toResponse(u);
    }

    /* 更新用户名 / 头像（前端传哪个就更新哪个），返回最新资料 */
    @Transactional
    public UserDtos.ProfileResponse updateProfile(Integer userId, UserDtos.UpdateProfileRequest req) {
        var u = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (req.getUserName() != null) {
            var name = req.getUserName().trim();
            if (!name.isEmpty()) u.setUserName(name);
        }
        if (req.getPicture() != null) {
            var pic = req.getPicture().trim();   // 这里应是 URL（例如 /files/avatars/xxx.jpg）
            if (!pic.isEmpty()) u.setPicture(pic);
        }
        // 如需支持修改邮箱，可放开：
        // if (req.getEmail() != null) {
        //     var email = req.getEmail().trim();
        //     if (!email.isEmpty()) u.setEmail(email);
        // }

        repo.save(u);
        return toResponse(u);
    }

    /* 只改新密码（不需要旧密码），最小长度做个简单校验 */
    @Transactional
    public void changePassword(Integer userId, UserDtos.ChangePasswordRequest req) {
        var newPwd = req.getNewPassword();
        if (newPwd == null || newPwd.isBlank()) {
            throw new IllegalArgumentException("New password cannot be blank");
        }
        if (newPwd.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }

        var u = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        u.setPassword(encoder.encode(newPwd));
        repo.save(u);
    }

    /* 专门给“上传头像接口”用：直接写入 picture URL，返回最新资料 */
    @Transactional
    public UserDtos.ProfileResponse updateAvatar(Integer userId, String pictureUrl) {
        var u = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        if (pictureUrl != null && !pictureUrl.isBlank()) {
            u.setPicture(pictureUrl.trim());
            repo.save(u);
        }
        return toResponse(u);
    }

    /* 实体 -> 响应 DTO（按你的 DTO 字段对齐） */
    private static UserDtos.ProfileResponse toResponse(UserInfoEntity u) {
        var dto = new UserDtos.ProfileResponse();
        // ⚠️ 这里用 getUserId() 而不是 getId()
        dto.setId(u.getId());
        dto.setUserName(u.getUserName());
        dto.setPicture(u.getPicture());
        dto.setEmail(u.getEmail()); // 不想返回邮箱可删掉这一行
        return dto;
    }
}