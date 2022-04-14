package com.vivacon.dto.response;

import com.vivacon.common.enum_type.Privacy;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.AuditableResponse;

import java.util.List;

public class NewsfeedPost extends AuditableResponse {

    private Long id;

    private String caption;

    private Privacy privacy;

    private List<AttachmentDTO> attachments;

    private Long likeCount;

    private Long commentCount;

    public NewsfeedPost() {

    }

    public NewsfeedPost(Long id, String caption, Privacy privacy) {
        this.id = id;
        this.caption = caption;
        this.privacy = privacy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
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
