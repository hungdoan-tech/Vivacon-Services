package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.CommentReport;
import com.vivacon.service.CommentReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

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

    @ApiOperation(value = "Get list comment report based on criteria")
    @GetMapping()
    public PageDTO<CommentReport> getAll(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return commentReportService.getAll(order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Deleting a comment report")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCommentReport(@PathVariable(name = "id") Long id) {
        this.commentReportService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
