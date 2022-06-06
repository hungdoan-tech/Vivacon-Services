package com.vivacon.repository;

import com.vivacon.dto.response.TopHashTagResponse;
import com.vivacon.entity.HashTagRelPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagRelPostRepository extends JpaRepository<HashTagRelPost, Long> {
    @Query("SELECT new com.vivacon.dto.response.TopHashTagResponse(hashTagRel.hashTag.id, hashTag.name, COUNT(hashTagRel.id)) " +
            "FROM HashTagRelPost hashTagRel " +
            "GROUP BY hashTagRel.hashTag.id, hashTag.name " +
            "ORDER BY COUNT(hashTagRel.id) DESC")
    List<TopHashTagResponse> findTopHashTag();

}
