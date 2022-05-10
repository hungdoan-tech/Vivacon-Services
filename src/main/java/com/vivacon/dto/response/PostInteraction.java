package com.vivacon.dto.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class PostInteraction {

    private BigInteger postId;

    private String caption;

    private LocalDateTime createdAt;

    private String userName;

    private String fullName;

    private String url;

    private BigInteger totalComment;

    private BigInteger totalLike;

    private BigInteger totalInteraction;

    public PostInteraction(BigInteger postId, String caption, LocalDateTime createdAt, String userName, String fullName, String url, BigInteger totalComment, BigInteger totalLike, BigInteger totalInteraction) {
        this.postId = postId;
        this.caption = caption;
        this.createdAt = createdAt;
        this.userName = userName;
        this.fullName = fullName;
        this.url = url;
        this.totalComment = totalComment;
        this.totalLike = totalLike;
        this.totalInteraction = totalInteraction;
    }

    public BigInteger getPostId() {
        return postId;
    }

    public void setPostId(BigInteger postId) {
        this.postId = postId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigInteger getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(BigInteger totalComment) {
        this.totalComment = totalComment;
    }

    public BigInteger getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(BigInteger totalLike) {
        this.totalLike = totalLike;
    }

    public BigInteger getTotalInteraction() {
        return totalInteraction;
    }

    public void setTotalInteraction(BigInteger totalInteraction) {
        this.totalInteraction = totalInteraction;
    }
}
