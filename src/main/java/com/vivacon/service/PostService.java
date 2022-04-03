package com.vivacon.service;

import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.dto.sorting_filtering.PostFilter;

import java.util.Optional;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);

    PageDTO<PostResponse> getAll(PostFilter innovationFilter, Optional<String> keyword, Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
