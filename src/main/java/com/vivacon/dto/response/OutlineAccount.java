package com.vivacon.dto.response;

public class OutlineAccount {

    private Long id;

    private String userName;

    private String fullName;

    private String avatar;

    private Boolean isFollowing;

    public OutlineAccount() {

    }

    public OutlineAccount(Long id, String userName, String fullName, String avatar, Boolean isFollowing) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.avatar = avatar;
        this.isFollowing = isFollowing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

}
