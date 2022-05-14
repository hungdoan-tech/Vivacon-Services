package com.vivacon.dao;

import com.vivacon.dto.response.UserAccountMostFollower;

import java.util.List;

public interface UserStatisticDAO {

    List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(int limit);

}
