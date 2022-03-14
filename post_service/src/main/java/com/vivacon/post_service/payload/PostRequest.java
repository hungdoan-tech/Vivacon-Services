package com.vivacon.post_service.payload;

import lombok.Data;

@Data
public class PostRequest {

    private String imageUrl;
    private String caption;
}
