package com.example.mynotebook.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * 使用 BCrypt 校验密码；数据库中应存放 BCrypt 哈希（通过 encoder.encode(raw) 生成）。
     */
    public Optional<UserInfoEntity> login(String email, String rawPassword) {
        if (email == null || rawPassword == null) return Optional.empty();
        return userInfoRepository.findByEmail(email)
                .filter(user -> {
                    String storedHash = user.getPassword();
                    return storedHash != null && encoder.matches(rawPassword, storedHash);
                });
    }

    public boolean existsByEmail(String email) {
        return userInfoRepository.existsByEmail(email);
    }

    /**
     * 注册：密码加密存储；若未提供 userName（null/空白），保存后用 "user{ID}" 回填。
     */
    public UserInfoEntity register(String email, String rawPassword, String userName, String picture) {
        String hash = encoder.encode(rawPassword);

        // 先按传入值构建（对 userName 做 trim；空白当作未提供）
        String normalizedName = (userName != null && !userName.trim().isEmpty()) ? userName.trim() : null;

        UserInfoEntity entity = UserInfoEntity.builder()
                .email(email)
                .password(hash)
                .userName(normalizedName)
                .picture(picture)
                .build();

        // 1) 先保存以获得自增 ID
        UserInfoEntity saved = userInfoRepository.save(entity);

        // 2) 若没名字，则回写 "user{ID}"
        if (saved.getUserName() == null || saved.getUserName().isBlank()) {
            // 如果你的实体主键 getter 叫 getId()，把下面这一行改成 getId()
            String defaultName = "user" + saved.getId();
            saved.setUserName(defaultName);
            saved = userInfoRepository.save(saved);
        }

        return saved;
    }
}