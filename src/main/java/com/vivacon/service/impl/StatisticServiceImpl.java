package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.StatisticDAO;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    private StatisticDAO statisticDAO;

    @Autowired
    public StatisticServiceImpl(
            StatisticDAO statisticDAO
    ) {
        this.statisticDAO = statisticDAO;
    }

    @Override
    public List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        return this.statisticDAO.getThePostQuantityStatisticInTimePeriods(timePeriodOption);
    }
}
