package com.vivacon.service;

import com.vivacon.dto.request.PostRequest;
import com.vivacon.dto.response.PostResponse;

public interface PostService {

    PostResponse createPost(PostRequest postRequest);
}
