package com.vivacon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vivacon.common.enum_type.PrivacyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "author")
    private Account author;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy_type")
    private PrivacyType privacyType;

    public Post() {

    }

    public Post(Long id, Account author, String caption, PrivacyType privacyType) {
        this.id = id;
        this.author = author;
        this.caption = caption;
        this.privacyType = privacyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
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
