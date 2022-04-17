package com.vivacon.service;

import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.dto.sorting_filtering.PostFilter;

import java.util.Optional;

public interface PostService {

    NewsfeedPost createPost(PostRequest postRequest);

    PageDTO<NewsfeedPost> getAll(PostFilter postFilter, Optional<String> keyword, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
