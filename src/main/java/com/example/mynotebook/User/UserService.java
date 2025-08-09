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

    public UserInfoEntity register(String email, String rawPassword, String userName, String picture) {
        String hash = encoder.encode(rawPassword);
        UserInfoEntity entity = UserInfoEntity.builder()
                .email(email)
                .password(hash) // 加密后存储
                .userName(userName)
                .picture(picture)
                .build();
        return userInfoRepository.save(entity);
    }
}
