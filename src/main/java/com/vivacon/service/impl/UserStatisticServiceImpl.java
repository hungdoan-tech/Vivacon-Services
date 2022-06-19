package com.vivacon.service.impl;

import com.vivacon.dao.UserStatisticDAO;
import com.vivacon.dto.response.UserAccountMostFollowerResponse;
import com.vivacon.mapper.PageMapper;
import com.vivacon.mapper.UserAccountMostFollowerMapper;
import com.vivacon.service.UserStatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticServiceImpl implements UserStatisticService {

    private UserStatisticDAO userStatisticDAO;

    private UserAccountMostFollowerMapper accountMostFollowerMapper;

    public UserStatisticServiceImpl(UserStatisticDAO userStatisticDAO, UserAccountMostFollowerMapper accountMostFollowerMapper) {
        this.userStatisticDAO = userStatisticDAO;
        this.accountMostFollowerMapper = accountMostFollowerMapper;
    }

    @Override
    public List<UserAccountMostFollowerResponse> getTheTopAccountMostFollowerStatistic(Integer limit) {
        return PageMapper.toDTOs(this.userStatisticDAO.getTheTopAccountMostFollowerStatistic(limit), accountMostFollower -> accountMostFollowerMapper.toUserAccountMostFollower(accountMostFollower));
    }
}
