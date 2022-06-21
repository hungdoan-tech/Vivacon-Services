package com.vivacon.entity;

import com.vivacon.common.enum_type.Gender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_generator")
    @SequenceGenerator(name = "account_id_generator", sequenceName = "account_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "bio", length = 200)
    private String bio;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "token_expired_date")
    private Instant tokenExpiredDate;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    @Column(name = "verification_expired_date")
    private Instant verificationExpiredDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public Account() {

    }

    public Account(String username, String email, String password, String fullName, Role role, String bio, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.bio = bio;
        this.active = active;
        this.createdAt = LocalDateTime.now();
    }

    public Account(String username, String email, String password, String fullName, Role role, String bio, String phoneNumber, Gender gender, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.active = active;
        this.createdAt = LocalDateTime.now();
    }

    public Account(Long id, String username, String email, String password, String fullName, Role role, String bio, String refreshToken, Instant tokenExpiredDate, String verificationToken, Instant verificationExpiredDate, String phoneNumber, Gender gender, Account createdBy, LocalDateTime createdAt, Account lastModifiedBy, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.bio = bio;
        this.refreshToken = refreshToken;
        this.tokenExpiredDate = tokenExpiredDate;
        this.verificationToken = verificationToken;
        this.verificationExpiredDate = verificationExpiredDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedAt = LocalDateTime.now();
        this.active = active;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getTokenExpiredDate() {
        return tokenExpiredDate;
    }

    public void setTokenExpiredDate(Instant expiryDate) {
        this.tokenExpiredDate = expiryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Instant getVerificationExpiredDate() {
        return verificationExpiredDate;
    }

    public void setVerificationExpiredDate(Instant verificationExpiredDate) {
        this.verificationExpiredDate = verificationExpiredDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public static class AccountBuilder {

        private String username;

        private String email;

        private String password;

        private String fullName;

        private Role role;

        private String bio;

        private String phoneNumber;

        private Gender gender;

        private boolean active;

        public AccountBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder username() {
            if (this.fullName != null) {
                this.username = fullName.replace(" ", "") + UUID.randomUUID();
                return this;
            } else {
                this.username = UUID.randomUUID().toString();
                return this;
            }
        }

        public AccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public AccountBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public AccountBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public AccountBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public AccountBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public AccountBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public Account build() {
            return new Account(username, email, password, fullName, role, bio, phoneNumber, gender, active);
        }
    }
}
