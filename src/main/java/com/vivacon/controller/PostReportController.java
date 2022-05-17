package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.entity.PostReport;
import com.vivacon.service.PostReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Post Report Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/postReport")
public class PostReportController {

    private PostReportService postReportService;

    public PostReportController(PostReportService postReportService) {
        this.postReportService = postReportService;
    }

    /**
     * This endpoint is used to provide creating post report feature
     *
     * @param postReportRequest
     * @return PostReport
     */
    @ApiOperation(value = "Creating post report")
    @PostMapping()
    public PostReport createPostReport(@Valid @RequestBody PostReportRequest postReportRequest) {
        return this.postReportService.createPostReport(postReportRequest);
    }

}
