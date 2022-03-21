package com.vivacon.dto;

public class PostDTO {

    private Long id;

    private String authorName;

    private String caption;

    private Long privacyType;

    public PostDTO() {

    }

    public PostDTO(Long id, String authorName, String caption, Long privacyType) {
        this.id = id;
        this.authorName = authorName;
        this.caption = caption;
        this.privacyType = privacyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getPrivacyType() {
        return privacyType;
    }

    public void setPrivacyType(Long privacyType) {
        this.privacyType = privacyType;
    }
}
