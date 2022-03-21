package com.vivacon.dto.request;

import com.vivacon.common.enum_type.PrivacyType;
import com.vivacon.entity.Attachment;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class PostRequest {

    private List<Attachment> lstAttachments;

    @NotBlank
    private String caption;

    private PrivacyType privacyType;

    public PostRequest() {

    }

    public PostRequest(List<Attachment> lstAttachments, String caption, PrivacyType privacyType) {
        this.lstAttachments = lstAttachments;
        this.caption = caption;
        this.privacyType = privacyType;
    }

    public List<Attachment> getLstAttachments() {
        return lstAttachments;
    }

    public void setLstAttachments(List<Attachment> lstAttachments) {
        this.lstAttachments = lstAttachments;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public PrivacyType getPrivacyType() {
        return privacyType;
    }

    public void setPrivacyType(PrivacyType privacyType) {
        this.privacyType = privacyType;
    }
}
