package com.example.mynotebook.Share;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    // 扁平：按时间升序返回该分享贴的所有评论
    List<CommentEntity> findBySharingIdOrderByCreateTimeAsc(Integer sharingId);

    // 可选：只取一级
    List<CommentEntity> findBySharingIdAndPreCommentIdIsNullOrderByCreateTimeAsc(Integer sharingId);

    // 可选：按父评论取子评论
    List<CommentEntity> findBySharingIdAndPreCommentIdOrderByCreateTimeAsc(Integer sharingId, Integer preCommentId);
}