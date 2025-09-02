package com.example.mynotebook.Share;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ShareRepository extends JpaRepository<ShareEntity, Integer> {
    List<ShareEntity> findAllByOrderByCreateTimeDesc();
    List<ShareEntity> findByUser_IdOrderByCreateTimeDesc(Integer userId);
    @Modifying
    @Transactional
    @Query("update ShareEntity s set s.likes = s.likes + 1 where s.id = :id")
    void increaseLikes(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update ShareEntity s set s.likes = case when s.likes > 0 then s.likes - 1 else 0 end where s.id = :id")
    void decreaseLikes(@Param("id") Integer id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update ShareEntity s set s.comments = s.comments + 1 where s.id = :id")
    int incComments(@Param("id") Integer id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update ShareEntity s set s.comments = s.comments - 1 where s.id = :id and s.comments >= 0")
    int decComments(@Param("id") Integer id);

    @Query("select s from ShareEntity s order by s.likes desc")
    List<ShareEntity> findAllOrderByLikesDesc();

    
}