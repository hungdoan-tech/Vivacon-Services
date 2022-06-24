package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostStatisticDAO;
import com.vivacon.dao.UserStatisticDAO;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.response.PostInteractionDTO;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.dto.response.UserAccountMostFollower;
import com.vivacon.mapper.PageMapper;
import com.vivacon.mapper.PostMapper;
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

    private PostMapper postMapper;

    public StatisticServiceImpl(PostRepository postRepository,
                                AccountRepository accountRepository,
                                UserStatisticDAO userStatisticDAO,
                                PostStatisticDAO postStatisticDAO,
                                PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.userStatisticDAO = userStatisticDAO;
        this.postStatisticDAO = postStatisticDAO;
        this.postMapper = postMapper;
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
    public List<PostInteractionDTO> getTheTopPostInteraction(Integer limit, Integer pageIndex) {
        return PageMapper.toDTOs(this.postStatisticDAO.getTheTopPostInteraction(limit, pageIndex), post -> this.postMapper.toPostInteraction(post));
    }

    @Override
    public List<OutlinePost> getTheTopTrendingPost(Integer limit, Integer pageIndex) {
        return PageMapper.toDTOs(this.postStatisticDAO.getTheTopPostInteraction(limit, pageIndex), post -> this.postMapper.toOutlinePost(post));
    }

    @Override
    public List<PostNewest> getTopNewestPost(Integer limit) {
        return this.postStatisticDAO.getTopNewestPost(limit);
    }
}
