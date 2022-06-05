package com.vivacon.service;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.dto.response.UserAccountMostFollower;

import java.util.List;

public interface StatisticService {

    StatisticDataQuantity getStatisticData();

    List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit);

    List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);

    List<PostInteraction> getTheTopPostInteraction(Integer limit);

    List<PostNewest> getTopNewestPost(Integer limit);

}
