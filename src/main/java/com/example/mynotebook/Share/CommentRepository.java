package com.example.mynotebook.Share;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    // 扁平：按时间升序返回该分享贴的所有评论
    List<CommentEntity> findBySharingIdOrderByCreateTimeAsc(Integer sharingId);

    // 可选：只取一级
    List<CommentEntity> findBySharingIdAndPreCommentIdIsNullOrderByCreateTimeAsc(Integer sharingId);

    // 可选：按父评论取子评论
    List<CommentEntity> findBySharingIdAndPreCommentIdOrderByCreateTimeAsc(Integer sharingId, Integer preCommentId);

    @Query("select c.commentId from CommentEntity c where c.preCommentId = :pid")
    List<Integer> findIdsByParent(@Param("pid") Integer parentId);

    @Modifying
    @Transactional
    @Query("update ShareEntity s set s.comments = case when s.comments >= :n then s.comments - :n else 0 end where s.id = :id")
    int decCommentsBy(@Param("id") Integer id, @Param("n") int n);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM comments WHERE sharing_id = :sid", nativeQuery = true)
    int deleteAllBySharingId(@Param("sid") Integer sharingId);
}