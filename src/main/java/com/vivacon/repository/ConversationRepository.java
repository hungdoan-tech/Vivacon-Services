package com.vivacon.repository;

import com.vivacon.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query( "Select c from Conversation c " +
            "where " +
            "exists (SELECT c FROM Conversation c join Participant p on c.id = p.conversation.id WHERE p.account.username = :principalUsername) " +
            "and c.name like '%:keyword%'" )
    Page<Conversation> findByApproximatelyName(@Param("keyword") String keyword, @Param("principalUsername") String principalUsername, Pageable pageable);
}
