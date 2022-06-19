package com.vivacon.service;

import com.vivacon.dto.response.UserAccountMostFollowerResponse;

import java.util.List;

public interface UserStatisticService {

    List<UserAccountMostFollowerResponse> getTheTopAccountMostFollowerStatistic(Integer limit);

}
