package com.vivacon.dto.response;

public class OutlinePost {

    private String firstImage;

    private Boolean isMultipleImages;

    private Long likeCount;

    private Long commentCount;

    public OutlinePost() {
        
    }

    public OutlinePost(String firstImage, Boolean isMultipleImages, Long likeCount, Long commentCount) {
        this.firstImage = firstImage;
        this.isMultipleImages = isMultipleImages;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public Boolean getMultipleImages() {
        return isMultipleImages;
    }

    public void setMultipleImages(Boolean multipleImages) {
        isMultipleImages = multipleImages;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
