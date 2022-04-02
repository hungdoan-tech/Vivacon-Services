package com.vivacon.entity;

import com.vivacon.common.enum_type.Privacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "privacy")
    private Privacy privacy;

    public Post() {

    }

    public Post(Long id, String caption, Privacy privacyType) {
        this.id = id;
        this.caption = caption;
        this.privacy = privacyType;
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

    public void setPrivacy(Privacy privacyType) {
        this.privacy = privacyType;
    }
}
