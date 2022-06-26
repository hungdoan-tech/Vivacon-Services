package com.vivacon.service.impl;

import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.common.enum_type.TimeSection;
import com.vivacon.dao.HashTagDAO;
import com.vivacon.dao.PostDAO;
import com.vivacon.dao.UserDAO;
import com.vivacon.dto.response.HashTagQuantityInCertainTime;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticServiceImpl implements StatisticService {

    private PostRepository postRepository;

    private AccountRepository accountRepository;

    private UserDAO userDAO;

    private PostDAO postDAO;

    private HashTagDAO hashTagDAO;

    private PostMapper postMapper;

    public StatisticServiceImpl(PostRepository postRepository,
                                AccountRepository accountRepository,
                                UserDAO userDAO,
                                PostDAO postDAO,
                                HashTagDAO hashTagDAO,
                                PostMapper postMapper) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.hashTagDAO = hashTagDAO;
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

    @Override
    public List<HashTagQuantityInCertainTime> getTopTrendingHashTagInCertainTime(Optional<TimeSection> timeSection, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate, Integer limit) {

        if (timeSection.isPresent()) {
            List<LocalDateTime> localDateTimes = getStartDateAndEndDate(timeSection.get());
            LocalDateTime startDateTime = localDateTimes.get(0);
            LocalDateTime endDateTime = localDateTimes.get(1);

            return hashTagDAO.getTopTrendingHashTagInCertainTime(startDateTime, endDateTime, limit);
        }

        return null;
    }

    private List<LocalDateTime> getStartDateAndEndDate(TimeSection timeSection) {

        List<LocalDateTime> localDateTimeList = new ArrayList<>();

        LocalDateTime startDate = null;
        LocalDateTime endDate = LocalDateTime.now();

        switch (timeSection) {
            case DAY: {
                startDate = endDate.minus(1, ChronoUnit.DAYS);
            }
            case WEEK: {
                startDate = endDate.minus(1, ChronoUnit.WEEKS);
            }
            case MONTH: {
                startDate = endDate.minus(1, ChronoUnit.MONTHS);
            }
            case YEAR: {
                startDate = endDate.minus(1, ChronoUnit.YEARS);
            }
            default: {
                startDate = endDate.minus(1, ChronoUnit.YEARS);
            }
        }

        localDateTimeList.add(startDate);
        localDateTimeList.add(endDate);

        return localDateTimeList;
    }
}
