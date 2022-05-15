package com.vivacon.repository;

import com.vivacon.entity.Account;
import com.vivacon.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("select p.account from Participant p where p.conversation.id = :conversationId")
    List<Account> getParticipantsByConversationId(@Param("conversationId") long conversationId);
}
