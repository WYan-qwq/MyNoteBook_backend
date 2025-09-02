package com.example.mynotebook.Share;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShareLikeRepository extends JpaRepository<ShareLikeEntity, Integer> {
    boolean existsByShare_IdAndUser_Id(Integer shareId, Integer userId);
    long countByShare_Id(Integer shareId);
    Optional<ShareLikeEntity> findByShare_IdAndUser_Id(Integer shareId, Integer userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM share_like WHERE share_id = :sid", nativeQuery = true)
    int deleteAllBySharingId(@Param("sid") Integer sharingId);
}