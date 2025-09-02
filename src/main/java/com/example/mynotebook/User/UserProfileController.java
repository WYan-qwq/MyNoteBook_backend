package com.example.mynotebook.User;

import com.example.mynotebook.User.DTO.UserDtos;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping("/{id}/profile")
    public UserDtos.ProfileResponse getProfile(@PathVariable Integer id) {
        return service.getProfile(id);
    }

    @PutMapping("/{id}/profile")
    public UserDtos.ProfileResponse updateProfile(
            @PathVariable Integer id,
            @RequestBody UserDtos.UpdateProfileRequest req
    ) {
        return service.updateProfile(id, req);
    }

    @PutMapping("/{id}/password")
    public Map<String, Object> changePassword(
            @PathVariable Integer id,
            @RequestBody UserDtos.ChangePasswordRequest req
    ) {
        service.changePassword(id, req);
        return Map.of("ok", true);
    }

    /** 头像上传：保存到 uploads/avatars 目录，并返回可访问的 URL 和最新用户资料 */
    @PostMapping("/{id}/avatar")
    public ResponseEntity<Map<String, Object>> uploadAvatar(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "file is empty"));
        }

        // 生成文件名
        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf('.')); // 带点，比如 ".jpg"
        }
        String filename = UUID.randomUUID() + ext;

        // 保存目录 uploads/avatars
        Path uploadRoot = Paths.get("uploads").toAbsolutePath().normalize();
        Path avatarDir = uploadRoot.resolve("avatars");
        Files.createDirectories(avatarDir);

        // 保存文件
        Path target = avatarDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // 构造对外可访问的 URL（和 WebStaticConfig 中的 /files/** 映射一致）
        String publicUrl = "/files/avatars/" + filename;

        // ✅ 调用实例方法更新用户 picture 字段
        var view = service.updateAvatar(id, publicUrl);

        return ResponseEntity.ok(Map.of(
                "url", publicUrl,
                "user", view
        ));
    }
}
