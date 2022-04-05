package com.vivacon.repository;

import com.vivacon.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Following following WHERE following.from.id = :fromId AND following.to.id = :toId")
    void unfollowById(@Param("fromId") long fromId, @Param("toId") long toId);

    @Query("SELECT following FROM Following following WHERE following.from.id = :fromId AND following.to.id = :toId")
    Optional<Following> findByIdComposition(@Param("fromId") long fromId, @Param("toId") long toId);
}
