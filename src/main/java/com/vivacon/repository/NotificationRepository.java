package com.vivacon.repository;

import com.vivacon.entity.Notification;
import com.vivacon.entity.enum_type.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByTypeAndDomainId(NotificationType type, Long id);

    Page<Notification> findByReceiverId(Long receiverId, Pageable pageable);
}
