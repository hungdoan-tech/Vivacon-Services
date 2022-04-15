package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.NewsfeedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.BAD_REQUEST_COMMON_MESSAGE;
import static com.vivacon.common.constant.Constants.FETCHING_SUCCESSFULLY;

@RestController
@RequestMapping(Constants.API_V1)
public class NewsfeedController {

    private NewsfeedService newsfeedService;

    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @ApiOperation(value = "Get newsfeed of the current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FETCHING_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @GetMapping("/newsfeed")
    public PageDTO<NewsfeedPost> getNewsfeed(
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return newsfeedService.getNewsfeed(pageSize, pageIndex);
    }
}
