package com.vivacon.service;

public interface FollowingService {

    boolean follow(Long toAccountId);

    boolean unfollow(Long toAccountId);
}
