package com.vivacon.controller.statistic;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Statistic Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/statistic")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @ApiOperation(value = "Get the statistic data")
    @GetMapping("/getStatisticData")
    public StatisticDataQuantity getStatisticData() {
        return this.statisticService.getStatisticData();
    }
}
