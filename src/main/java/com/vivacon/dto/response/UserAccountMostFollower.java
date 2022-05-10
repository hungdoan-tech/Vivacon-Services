package com.vivacon.dto.response;

import java.math.BigInteger;

public class UserAccountMostFollower {

    private BigInteger id;

    private String userName;

    private String url;

    private BigInteger accountQuantity;

    public UserAccountMostFollower(BigInteger id, String userName, String url, BigInteger accountQuantity) {
        this.id = id;
        this.userName = userName;
        this.url = url;
        this.accountQuantity = accountQuantity;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigInteger getAccountQuantity() {
        return accountQuantity;
    }

    public void setAccountQuantity(BigInteger accountQuantity) {
        this.accountQuantity = accountQuantity;
    }
}
