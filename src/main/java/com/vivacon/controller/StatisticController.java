package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostInteraction;
import com.vivacon.dto.response.PostNewest;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.dto.response.UserAccountMostFollower;
import com.vivacon.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "Post Statistic Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/statistic")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @ApiOperation(value = "Get the general statistic data")
    @GetMapping("/getStatisticData")
    public StatisticDataQuantity getStatisticData() {
        return this.statisticService.getStatisticData();
    }

    @ApiOperation(value = "Get top account most followers statistic")
    @GetMapping("/user/most/followers")
    public List<UserAccountMostFollower> getTheTopAccountMostFollowerStatistic(@RequestParam(value = "limit") Optional<Integer> limit) {
        return this.statisticService.getTheTopAccountMostFollowerStatistic(limit.orElse(5));
    }

    @ApiOperation(value = "Get post quantity statistic in recent months")
    @GetMapping("/post/in/months")
    public List<PostsQuantityInCertainTime> getPostQuantityStatisticInMonths() {
        return this.statisticService.getThePostQuantityStatisticInTimePeriods(TimePeriod.MONTH);
    }

    @ApiOperation(value = "Get post quantity statistic in recent quarters")
    @GetMapping("/post/in/quarters")
    public List<PostsQuantityInCertainTime> getPostQuantityStatisticInQuarters() {
        return this.statisticService.getThePostQuantityStatisticInTimePeriods(TimePeriod.QUARTER);
    }

    @ApiOperation(value = "Get post quantity statistic in recent years")
    @GetMapping("/post/in/years")
    public List<PostsQuantityInCertainTime> getPostQuantityStatisticInYears() {
        return this.statisticService.getThePostQuantityStatisticInTimePeriods(TimePeriod.YEAR);
    }

    @ApiOperation(value = "Get top posts interaction statistic")
    @GetMapping("/post/top/interaction")
    public List<PostInteraction> getTheTopPostInteraction(@RequestParam(value = "limit") Optional<Integer> limit,
                                                          @RequestParam(value = "pageIndex") Optional<Integer> pageIndex) {
        return this.statisticService.getTheTopPostInteraction(limit.orElse(5), pageIndex.orElse(0));
    }

    @ApiOperation(value = "Get top posts newest statistic")
    @GetMapping("/post/top/newest")
    public List<PostNewest> getPostByNewestCreatedAt(@RequestParam(value = "limit") Optional<Integer> limit) {
        return this.statisticService.getTopNewestPost(limit.orElse(5));
    }
}
