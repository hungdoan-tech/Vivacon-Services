package com.vivacon.controller;

import com.vivacon.dto.response.NotificationResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.API_V1;

@RestController
@Api(value = "Notification endpoints")
@RequestMapping(API_V1)
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiOperation(value = "Get all notifications of current user in each page")
    @GetMapping("/notification")
    public PageDTO<NotificationResponse> findAllNotificationByPrincipal(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return notificationService.findAllByPrincipal(order, sort, pageSize, pageIndex);
    }
}
