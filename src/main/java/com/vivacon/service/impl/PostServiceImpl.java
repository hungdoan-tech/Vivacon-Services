package com.vivacon.service.impl;

import com.vivacon.dto.PostDTO;
import com.vivacon.dto.request.PostRequest;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostRequest postRequest) {
        //PostDTO post = new PostDTO(postRequest.getImageUrl(), postRequest.getCaption());
        PostDTO post = null;
        //post = postRepository.save(post);

//        logger.info("post {} is saved successfully for user {}",
//                post.getId(), post.getUsername());

        return post;
    }
}
