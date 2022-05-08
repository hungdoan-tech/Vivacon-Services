package com.vivacon.dto.response;

import java.math.BigInteger;

public class PostInteraction {

    private BigInteger postId;

    private String caption;

    private BigInteger totalComment;

    private BigInteger totalLike;

    private BigInteger totalInteraction;

    public PostInteraction(BigInteger postId, String caption, BigInteger totalComment, BigInteger totalLike, BigInteger totalInteraction) {
        this.postId = postId;
        this.caption = caption;
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
