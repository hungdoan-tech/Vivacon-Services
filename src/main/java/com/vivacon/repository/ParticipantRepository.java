package com.vivacon.repository;

import com.vivacon.entity.Conversation;
import com.vivacon.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    /**
     * This method signature is used to provide a list of conversations which have exactly the matched rows when searching for
     * its participants with input provided participants
     * @param participants
     * @param count
     * @return
     */
    @Query("SELECT p.conversation FROM Participant p WHERE p.account.username IN :participants GROUP BY p.conversation HAVING COUNT(p.id) = :count")
    List<Conversation> findByParticipantsUsername(@Param("participants") List<String> participants, @Param("count") Long count);
}
