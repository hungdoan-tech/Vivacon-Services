package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.response.UserAccountMostFollowerResponse;
import com.vivacon.service.UserStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "User Account Statistic Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/statistic")
public class UserStatisticController {

    private UserStatisticService userStatisticService;

    public UserStatisticController(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @ApiOperation(value = "Get top account most followers statistic")
    @GetMapping("/user/most/followers")
    public List<UserAccountMostFollowerResponse> getTheTopAccountMostFollowerStatistic(@RequestParam(value = "limit") Optional<Integer> limit) {
        return this.userStatisticService.getTheTopAccountMostFollowerStatistic(limit.orElse(5));
    }
}
