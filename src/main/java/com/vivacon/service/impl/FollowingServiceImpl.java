package com.vivacon.service.impl;

import com.vivacon.service.FollowingService;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl implements FollowingService {

    @Override
    public boolean follow(String toAccountId) {
        return false;
    }

    @Override
    public boolean unfollow(String toAccountId) {
        return false;
    }
}
