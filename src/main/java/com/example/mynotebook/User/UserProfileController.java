package com.example.mynotebook.User;

import com.example.mynotebook.User.DTO.UserDtos;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    /** 1) 获取头像/用户名（以及邮箱可选） */
    @GetMapping("/{id}/profile")
    public UserDtos.ProfileResponse getProfile(@PathVariable("id") Integer id) {
        return service.getProfile(id);
    }

    /** 2) 更改头像 / 用户名（传哪个改哪个） */
    @PutMapping("/{id}/profile")
    public Map<String, Object> updateProfile(
            @PathVariable("id") Integer id,
            @RequestBody UserDtos.UpdateProfileRequest req
    ) {
        service.updateProfile(id, req);
        return Map.of("ok", true, "id", id);
    }

    /** 3) 更改密码（BCrypt） */
    @PutMapping("/{id}/password")
    public Map<String, Object> changePassword(
            @PathVariable("id") Integer id,
            @RequestBody UserDtos.ChangePasswordRequest req
    ) {
        service.changePassword(id, req);
        return Map.of("ok", true, "id", id);
    }
}
