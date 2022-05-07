package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.common.enum_type.TimePeriod;
import com.vivacon.dto.response.PostsQuantityInCertainTime;
import com.vivacon.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Statistic Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/statistic")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
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
}
