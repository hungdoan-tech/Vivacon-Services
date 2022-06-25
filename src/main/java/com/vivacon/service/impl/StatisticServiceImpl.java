package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dao.PostDAO;
import com.vivacon.dao.UserDAO;
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

    private UserDAO userDAO;

    private PostDAO postDAO;

    private PostMapper postMapper;

    public StatisticServiceImpl(PostRepository postRepository,
                                AccountRepository accountRepository,
                                UserDAO userDAO,
                                PostDAO postDAO,
                                PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.postMapper = postMapper;
    }

    @Override
    public StatisticDataQuantity getStatisticData() {
        return new StatisticDataQuantity(postRepository.getAllPostCounting(), accountRepository.getAllAccountCounting());
    }

    @Override
    public List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(Integer limit) {
        return this.userDAO.getTheTopAccountMostFollowerStatistic(limit);
    }

    @Override
    public List<PostsQuantityInCertainTime> getThePostQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        return this.postDAO.getThePostQuantityStatisticInTimePeriods(timePeriodOption);
    }

    @Override
    public List<PostsQuantityInCertainTime> getTheUserQuantityStatisticInTimePeriods(TimePeriod timePeriodOption) {
        return this.userDAO.getTheUserQuantityStatisticInTimePeriods(timePeriodOption);
    }

    @Override
    public List<PostInteractionDTO> getTheTopPostInteraction(Integer limit, Integer pageIndex) {
        return PageMapper.toDTOs(this.postDAO.getTheTopPostInteraction(limit, pageIndex), post -> this.postMapper.toPostInteraction(post));
    }

    @Override
    public List<OutlinePost> getTheTopTrendingPost(Integer limit, Integer pageIndex) {
        return PageMapper.toDTOs(this.postDAO.getTheTopPostInteraction(limit, pageIndex), post -> this.postMapper.toOutlinePost(post));
    }

    @Override
    public List<PostNewest> getTopNewestPost(Integer limit) {
        return this.postDAO.getTopNewestPost(limit);
    }
}
