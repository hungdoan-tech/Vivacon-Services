package com.vivacon.service.impl;

import com.vivacon.common.enum_type.Privacy;
import com.vivacon.dto.response.NewsfeedPost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.dto.sorting_filtering.PostFilter;
import com.vivacon.entity.Account;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.NewsfeedService;
import com.vivacon.service.PostService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {

    private FollowingRepository followingRepository;

    private PostService postService;

    private AccountService accountService;

    public NewsfeedServiceImpl(FollowingRepository followingRepository,
                               PostService postService,
                               AccountService accountService) {
        this.postService = postService;
        this.followingRepository = followingRepository;
        this.accountService = accountService;
    }

    @Override
    public PageDTO<NewsfeedPost> getNewsfeed(Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Account currentAccount = accountService.getCurrentAccount();
        List<Account> followingAccounts = this.followingRepository.findFollowing(currentAccount.getId());
        List<Long> listAccountId = followingAccounts.stream().map(Account::getId).collect(Collectors.toList());
        PostFilter postFilter = new PostFilter(Optional.of(listAccountId), Optional.of(Arrays.asList(Privacy.PUBLIC,
                Privacy.FOLLOWER)), false, true);
        return postService.getAll(postFilter, Optional.empty(),
                Optional.of("DESC"), Optional.of("lastModifiedAt"), pageSize, pageIndex);
    }
}
