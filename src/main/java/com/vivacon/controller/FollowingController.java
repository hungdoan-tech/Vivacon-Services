package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.service.FollowingService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Following Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/following")
public class FollowingController {

    private FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @PostMapping("/{id}")
    private ResponseEntity<Object> followOneAccount(@PathVariable(name = "id") String toAccountId) {
        this.followingService.follow(toAccountId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> unfollowOneAccount(@PathVariable(name = "id") String toAccountId) {
        this.followingService.unfollow(toAccountId);
        return ResponseEntity.ok(null);
    }
}
