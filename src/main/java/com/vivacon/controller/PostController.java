package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
            @ApiResponse(code = 200, message = Constants.CREATE_SUCCESSFULLY),
            @ApiResponse(code = 401, message = Constants.BAD_CREDENTIALS)})
    @PostMapping()
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) {
        return this.postService.createPost(postRequest);
    }
}
