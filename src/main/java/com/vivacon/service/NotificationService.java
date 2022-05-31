package com.vivacon.service;

import com.vivacon.dto.response.NotificationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;

import java.util.Optional;

public interface NotificationService {

    PageDTO<NotificationResponse> findAllByPrincipal(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
