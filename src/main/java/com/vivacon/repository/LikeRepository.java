package com.vivacon.repository;

import com.vivacon.entity.Account;
import com.vivacon.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT liking.account, liking.id " +
            "FROM Like liking " +
            "WHERE liking.post.id = :postId")
    Page<Account> findAllLikeByAccount(@Param("postId") Long postId, Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Like liking WHERE liking.id = :id")
    void unlikeById(@Param("id") long id);
}
