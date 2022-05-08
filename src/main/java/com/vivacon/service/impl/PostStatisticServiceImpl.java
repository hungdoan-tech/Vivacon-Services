package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostStatisticDAO;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.service.PostStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostStatisticServiceImpl implements PostStatisticService {

    private PostStatisticDAO postStatisticDAO;

    @Autowired
    public PostStatisticServiceImpl(
            PostStatisticDAO postStatisticDAO
    ) {
        this.postStatisticDAO = postStatisticDAO;
    }

    @Override
    public List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        return this.postStatisticDAO.getThePostQuantityStatisticInTimePeriods(timePeriodOption);
    }

    @Override
    public List<PostInteraction> getTheTopPostInteraction(Integer limit) {
        return this.postStatisticDAO.getTheTopPostInteraction(limit);
    }

    @Override
    public List<PostNewest> getTopNewestPost(Integer limit) {
        return this.postStatisticDAO.getTopNewestPost(limit);
    }
}
