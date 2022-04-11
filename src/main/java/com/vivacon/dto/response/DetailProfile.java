package com.vivacon.dto.response;

import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;

public class DetailProfile {

    private Long id;

    private String username;

    private String fullName;

    private String avatar;

    private Long postCount;

    private Long followerCount;

    private Long followingCount;

    private PageDTO<OutlinePost> listPost;

    public DetailProfile(Account account, String avatar, Long postCount, Long followerCount, Long followingCount, PageDTO<OutlinePost> listPost) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.fullName = account.getFullName();
        this.avatar = avatar;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.listPost = listPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    public Long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

    public PageDTO<OutlinePost> getListPost() {
        return listPost;
    }

    public void setListPost(PageDTO<OutlinePost> listPost) {
        this.listPost = listPost;
    }
}