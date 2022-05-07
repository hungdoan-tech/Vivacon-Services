package com.vivacon.dao;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostsQuantityInCertainTime;

import java.util.List;

public interface StatisticDAO {
    List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption);
}
