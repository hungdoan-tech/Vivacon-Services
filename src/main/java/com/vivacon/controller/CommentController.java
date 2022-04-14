package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.BAD_REQUEST_COMMON_MESSAGE;
import static com.vivacon.common.constant.Constants.FETCHING_SUCCESSFULLY;

@Api(value = "Comment Controller")
@RestController
@RequestMapping(value = Constants.API_V1)
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Get list comment based on criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FETCHING_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @GetMapping(value = "/post/{postId}/first-level-comment")
    public PageDTO<CommentResponse> getAll(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex,
            @PathVariable(name = "postId", value = "postId", required = true) Long postId) {
        return commentService.getAll(sort, order, pageSize, pageIndex, postId);
    }

    @ApiOperation(value = "Get list comment based on criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FETCHING_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @GetMapping(value = "/post/{postId}/comment/{parentCommentId}/child-comment")
    public PageDTO<CommentResponse> getAllChildComment(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex,
            @PathVariable(name = "parentCommentId", value = "parentCommentId", required = true) Long parentCommentId,
            @PathVariable(name = "postId", value = "postId", required = true) Long postId) {
        return commentService.getAllChildComment(sort, order, pageSize, pageIndex, parentCommentId, postId);
    }
}
