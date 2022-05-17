package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.entity.CommentReport;
import com.vivacon.service.CommentReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Comment Report Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/commentReport")
public class CommentReportController {

    private CommentReportService commentReportService;

    public CommentReportController(CommentReportService commentReportService) {
        this.commentReportService = commentReportService;
    }

    /**
     * This endpoint is used to provide creating comment report feature
     *
     * @param commentReportRequest
     * @return CommentReport
     */
    @ApiOperation(value = "Creating comment report")
    @PostMapping()
    public CommentReport createCommentReport(@Valid @RequestBody CommentReportRequest commentReportRequest) {
        return this.commentReportService.createCommentReport(commentReportRequest);
    }

}
