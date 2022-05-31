package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.response.NotificationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Notification;
import com.vivacon.mapper.NotificationMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.NotificationRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private AccountService accountService;
    private NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   AccountService accountService,
                                   NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.accountService = accountService;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public PageDTO<NotificationResponse> findAllByPrincipal(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Notification.class);
        Account currentAccount = accountService.getCurrentAccount();
        Page<Notification> pageNotification = notificationRepository.findByReceiverId(currentAccount.getId(), pageable);
        return PageMapper.toPageDTO(pageNotification, notification -> notificationMapper.toResponse(notification));
    }
}
