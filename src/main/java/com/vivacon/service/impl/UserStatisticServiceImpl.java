package com.vivacon.service.impl;

import com.vivacon.dao.UserStatisticDAO;
import com.vivacon.dto.response.UserAccountMostFollower;
import com.vivacon.service.UserStatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticServiceImpl implements UserStatisticService {

    private UserStatisticDAO userStatisticDAO;

    public UserStatisticServiceImpl(UserStatisticDAO userStatisticDAO) {
        this.userStatisticDAO = userStatisticDAO;
    }

    @Override
    public List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit) {
        return this.userStatisticDAO.getTheTopAccountMostFollowerStatistic(limit);
    }
}
