package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.common.enum_type.Privacy;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.DetailPost;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.dto.sorting_filtering.PostFilter;
import com.vivacon.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vivacon.common.constant.Constants.BAD_REQUEST_COMMON_MESSAGE;
import static com.vivacon.common.constant.Constants.CREATE_SUCCESSFULLY;
import static com.vivacon.common.constant.Constants.FETCHING_SUCCESSFULLY;

@Api(value = "Post Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * This endpoint is used to provide creating post feature
     *
     * @param postRequest
     * @return PostResponse
     */
    @ApiOperation(value = "Creating post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CREATE_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @PostMapping()
    public NewsfeedPost createPost(@Valid @RequestBody PostRequest postRequest) {
        return this.postService.createPost(postRequest);
    }

    @ApiOperation(value = "Get list innovation/improvement/idea based on criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FETCHING_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @GetMapping()
    public PageDTO<NewsfeedPost> getAll(
            @RequestParam(value = "author", required = false) Optional<List<Long>> authors,
            @RequestParam(value = "privacy", required = false) Optional<List<Privacy>> statuses,
            @RequestParam(value = "own", required = false) Optional<Boolean> own,
            @RequestParam(value = "q", required = false) Optional<String> keyword,
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {

        PostFilter innovationFilter = new PostFilter(authors, statuses, own.orElse(false), true);
        return postService.getAll(innovationFilter, keyword, order, sort, pageSize, pageIndex);
    }

    @ApiOperation(value = "Get detail post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FETCHING_SUCCESSFULLY),
            @ApiResponse(code = 400, message = BAD_REQUEST_COMMON_MESSAGE)})
    @GetMapping(value = "/{id}")
    public DetailPost getDetailPost(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex,
            @PathVariable(name = "id") Long postId) {
        return postService.getDetailPost(order, sort, pageSize, pageIndex, postId);
    }
}






















