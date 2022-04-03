package com.vivacon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_id_generator")
    @SequenceGenerator(name = "attachment_id_generator", sequenceName = "attachment_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "actual_name")
    private String actualName;

    @Column(name = "unique_name")
    private String uniqueName;

    @JsonIgnore
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "innovation_id")
    private Post post;

    public Attachment() {

    }

    public Attachment(String actualName, String uniqueName, String url, Post post) {
        this.url = url;
        this.actualName = actualName;
        this.uniqueName = uniqueName;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post innovation) {
        this.post = innovation;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String name) {
        this.actualName = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }
}