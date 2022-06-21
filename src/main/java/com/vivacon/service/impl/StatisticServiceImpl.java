package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostStatisticDAO;
import com.vivacon.dao.UserStatisticDAO;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.dto.response.UserAccountMostFollower;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    private PostRepository postRepository;

    private AccountRepository accountRepository;

    private UserStatisticDAO userStatisticDAO;

    private PostStatisticDAO postStatisticDAO;

    public StatisticServiceImpl(PostRepository postRepository,
                                AccountRepository accountRepository,
                                UserStatisticDAO userStatisticDAO,
                                PostStatisticDAO postStatisticDAO) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.userStatisticDAO = userStatisticDAO;
        this.postStatisticDAO = postStatisticDAO;
    }

    @Override
    public StatisticDataQuantity getStatisticData() {
        return new StatisticDataQuantity(postRepository.getAllPostCounting(), accountRepository.getAllAccountCounting());
    }

    @Override
    public List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit) {
        return this.userStatisticDAO.getTheTopAccountMostFollowerStatistic(limit);
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
