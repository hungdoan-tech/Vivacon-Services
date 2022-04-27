package com.vivacon.repository;

import com.vivacon.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    @Query("SELECT comment FROM Comment comment WHERE comment.parentComment.id= :parentCommentId and comment.post.id= :postId AND comment.active = true")
    Page<Comment> findActiveChildCommentsByParentCommentId(@Param(value = "parentCommentId") Long parentCommentId, @Param(value = "postId") Long postId, Pageable pageable);

    @Query("SELECT comment FROM Comment comment WHERE comment.parentComment.id= :parentCommentId")
    Collection<Comment> findAllChildCommentsByParentCommentId(@Param(value = "parentCommentId") Long parentCommentId);

    @Query("SELECT comment FROM Comment comment WHERE comment.parentComment.id is null and comment.post.id= :postId AND comment.active = true")
    Page<Comment> findAllFirstLevelComments(@Param(value = "postId") Long postId, Pageable pageable);

    @Query("SELECT COUNT(comment.id) FROM Comment comment WHERE comment.parentComment.id = :parent_comment_id and comment.post.id= :post_id AND comment.active = true")
    Long getCountingChildComments(@Param(value = "parent_comment_id") Long parentCommentId, @Param(value = "post_id") Long postId);

    @Query("SELECT COUNT(comment.id) FROM Comment comment WHERE comment.post.id= :post_id AND comment.active = true")
    Long getCountingCommentsByPost(@Param(value = "post_id") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment comment SET comment.active = false WHERE comment.id = :id")
    int deactivateById(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment comment SET comment.active = false WHERE comment.parentComment.id = :parentCommentId")
    int deactivateChildCommentsByParentCommentId(@Param("parentCommentId") Long parentCommentId);
}
