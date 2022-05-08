package com.vivacon.repository;

import com.vivacon.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findFirstByRecipientIdOrderByTimestampDesc(Long postId);
}
