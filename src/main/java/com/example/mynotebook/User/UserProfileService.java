package com.example.mynotebook.User;

import com.example.mynotebook.User.DTO.UserDtos;
import jakarta.transaction.Transactional;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserProfileService {

    private final UserInfoRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserProfileService(UserInfoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public UserDtos.ProfileResponse getProfile(Integer id) {
        var u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        var dto = new UserDtos.ProfileResponse();
        dto.setId(u.getId());
        dto.setUserName(u.getUserName());
        dto.setPicture(u.getPicture());
        dto.setEmail(u.getEmail()); // 如果不想返回邮箱，可以去掉
        return dto;
    }

    @Transactional
    public void updateProfile(Integer id, UserDtos.UpdateProfileRequest req) {
        var u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        boolean changed = false;

        if (req.getUserName() != null) {
            u.setUserName(req.getUserName().trim());
            changed = true;
        }
        if (req.getPicture() != null) {
            u.setPicture(req.getPicture().trim());
            changed = true;
        }

        if (changed) {
            repo.save(u); // 也可用 repository 的 @Modifying 方法更新
        }
    }

    @Transactional
    public void changePassword(Integer id, UserDtos.ChangePasswordRequest req) {
        if (req.getNewPassword() == null || req.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("New password cannot be blank");
        }
        // 可选：最小长度校验
        if (req.getNewPassword().length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }

        var u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        u.setPassword(encoder.encode(req.getNewPassword()));
        repo.save(u);
    }
}