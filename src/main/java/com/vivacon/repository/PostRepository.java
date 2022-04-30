package com.vivacon.repository;

import com.vivacon.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    @Query("select p from Post p where p.createdBy.id = :account_id")
    Page<Post> findByAuthorId(@Param("account_id") Long accountId, Pageable pageable);

    @Query("select count(p.id) from Post p where p.createdBy.id = :account_id")
    Long getPostCountingByAccountId(@Param("account_id") Long accountId);

    @Query("select p from Post p where p.id = :post_id")
    Post findByPostId(@Param("post_id") Long postId);
}
