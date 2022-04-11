package com.vivacon.dto.response;

import com.vivacon.dto.sorting_filtering.PageDTO;

public class DetailProfile {

    private Long id;

    private String username;

    private String password;

    private String fullName;

    private Long postCount;

    private Long followerCount;

    private Long followingCount;

    private PageDTO<OutlinePost> listPost;
}