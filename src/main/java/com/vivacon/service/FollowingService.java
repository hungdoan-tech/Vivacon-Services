package com.vivacon.service;

public interface FollowingService {

    boolean follow(String toAccountId);

    boolean unfollow(String toAccountId);
}
