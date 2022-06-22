package com.vivacon.dto.response;

import java.time.LocalDateTime;

public class AccountAdminResponse {

    private Long id;

    private String email;

    private String username;

    private String fullName;

    private LocalDateTime createdAt;

    private String gender;

    private String phoneNumber;

    public AccountAdminResponse() {

    }

    public AccountAdminResponse(Long id, String email, String username, String fullName, LocalDateTime createdAt, String gender, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
