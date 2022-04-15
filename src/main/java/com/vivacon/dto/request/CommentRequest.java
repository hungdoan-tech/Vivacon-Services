package com.vivacon.dto.request;

import javax.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank
    private String content;

    private Long parentCommentId;

    private Long postId;

    public CommentRequest() {

    }

    public CommentRequest(String content, Long parentCommentId, Long postId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
