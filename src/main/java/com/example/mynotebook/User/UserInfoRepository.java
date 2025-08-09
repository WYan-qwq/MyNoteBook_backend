package com.example.mynotebook.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    Optional<UserInfoEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

