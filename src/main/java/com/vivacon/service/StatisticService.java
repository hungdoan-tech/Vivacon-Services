package com.vivacon.service;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostsQuantityInCertainTime;

import java.util.List;

public interface StatisticService {

    List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);

}
