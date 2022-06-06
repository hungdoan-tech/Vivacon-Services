package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.response.TopHashTagResponse;
import com.vivacon.service.HashTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.API_V1)
public class HashTagController {

    private HashTagService hashTagService;

    public HashTagController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    @ApiOperation(value = "Get top hashtag by post")
    @GetMapping("/hashtag/top")
    public List<TopHashTagResponse> findTopHashTag() {
        return hashTagService.findTopHashTag();
    }

}
