package com.vivacon.dto.response;

import com.vivacon.common.enum_type.Privacy;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.AuditableResponse;

import java.util.List;

public class PostResponse extends AuditableResponse {

    private Long id;

    private String caption;

    private Privacy privacy;

    private List<AttachmentDTO> attachments;

    public PostResponse() {

    }

    public PostResponse(Long id, String caption, Privacy privacy) {
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
}
