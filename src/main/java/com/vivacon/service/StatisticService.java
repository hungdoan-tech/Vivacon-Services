package com.vivacon.service;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.response.PostInteractionDTO;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.dto.response.UserAccountMostFollower;

import java.util.List;

public interface StatisticService {

    StatisticDataQuantity getStatisticData();

    List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit);

    List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);

    List<PostInteractionDTO> getTheTopPostInteraction(Integer limit, Integer pageIndex);

    List<OutlinePost> getTheTopTrendingPost(Integer limit, Integer pageIndex);

    List<PostNewest> getTopNewestPost(Integer limit);
}
