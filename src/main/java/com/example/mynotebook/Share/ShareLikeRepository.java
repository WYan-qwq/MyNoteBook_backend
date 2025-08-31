package com.example.mynotebook.Share;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareLikeRepository extends JpaRepository<ShareLikeEntity, Integer> {
    boolean existsByShare_IdAndUser_Id(Integer shareId, Integer userId);
    long countByShare_Id(Integer shareId);
    Optional<ShareLikeEntity> findByShare_IdAndUser_Id(Integer shareId, Integer userId);
}