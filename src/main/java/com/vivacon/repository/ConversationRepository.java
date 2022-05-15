package com.vivacon.repository;

import com.vivacon.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("Select c from Conversation c " +
            "where " +
            "exists (SELECT c FROM Conversation c join Participant p " +
            "on c.id = p.conversation.id " +
            "and p.account.username = :principalUsername) " +
            "and c.name like CONCAT('%',:keyword,'%')")
    Page<Conversation> findByApproximatelyName(@Param("keyword") String keyword, @Param("principalUsername") String principalUsername, Pageable pageable);

    @Query("Select c " +
            "from Conversation c " +
            "where " +
            "exists (SELECT c FROM Conversation c join Participant p on c.id = p.conversation.id WHERE p.account.username = :principalUsername) ")
    Page<Conversation> findAllIdByPrincipalUsername(@Param("principalUsername") String principalUsername, Pageable pageable);

    @Query("SELECT p.account.username FROM Conversation c join Participant p on c.id = p.conversation.id WHERE c.id = :conversationId")
    Optional<List<String>> findByAllParticipantsByConversationId(@Param("conversationId") Long conversationId);

    @Query("Select c.id " +
            "from Conversation c " +
            "where " +
            "exists (SELECT c FROM Conversation c join Participant p on c.id = p.conversation.id WHERE p.account.username = :principalUsername) ")
    List<Long> findAllIdByPrincipalUsername(@Param("principalUsername") String principalUsername);
}
