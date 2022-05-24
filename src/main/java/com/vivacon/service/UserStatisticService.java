package com.vivacon.service;

import com.vivacon.dto.response.UserAccountMostFollower;

import java.util.List;

public interface UserStatisticService {

    List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit);

}
