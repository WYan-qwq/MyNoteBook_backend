package com.example.mynotebook.Share;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<ShareEntity, Integer> {
    List<ShareEntity> findAllByOrderByCreateTimeDesc();
    List<ShareEntity> findByUser_IdOrderByCreateTimeDesc(Integer userId);
}