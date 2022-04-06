package com.vivacon.service;

import com.vivacon.entity.Account;

import java.util.List;
import java.util.Optional;

public interface FollowingService {

    boolean follow(Long toAccountId);

    boolean unfollow(Long toAccountId);

    List<Account> findFollower(Optional<Long> account);

    List<Account> findFollowing(Optional<Long> account);
}
