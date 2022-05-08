package com.vivacon.service;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;

import java.util.List;

public interface PostStatisticService {

    List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);

    List<PostInteraction> getTheTopPostInteraction(Integer limit);

    List<PostNewest> getTopNewestPost(Integer limit);

}
