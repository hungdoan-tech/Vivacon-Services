package com.vivacon.service;

import com.vivacon.dto.PostDTO;
import com.vivacon.dto.request.PostRequest;

public interface PostService {

    PostDTO createPost(PostRequest postRequest);
}
