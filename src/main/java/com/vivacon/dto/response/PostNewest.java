package com.vivacon.dto.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class PostNewest {

    private BigInteger id;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String caption;

    private Integer privacy;

    private BigInteger createdByAccountId;

    private BigInteger lastModifiedByAccountId;

    public PostNewest(BigInteger postId, Boolean isActived, LocalDateTime createdAt, LocalDateTime lastModifiedAt, String caption, Integer privacy, BigInteger createdByAccountId, BigInteger lastModifiedByAccountId) {
        id = postId;
        active = isActived;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.caption = caption;
        this.privacy = privacy;
        this.createdByAccountId = createdByAccountId;
        this.lastModifiedByAccountId = lastModifiedByAccountId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Integer privacy) {
        this.privacy = privacy;
    }

    public BigInteger getCreatedByAccountId() {
        return createdByAccountId;
    }

    public void setCreatedByAccountId(BigInteger createdByAccountId) {
        this.createdByAccountId = createdByAccountId;
    }

    public BigInteger getLastModifiedByAccountId() {
        return lastModifiedByAccountId;
    }

    public void setLastModifiedByAccountId(BigInteger lastModifiedByAccountId) {
        this.lastModifiedByAccountId = lastModifiedByAccountId;
    }
}
