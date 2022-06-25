package com.vivacon.dao;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.UserAccountMostFollower;

import java.util.List;

public interface UserStatisticDAO {

    List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(int limit);

    List<PostsQuantityInCertainTime> getTheUserQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);
}
