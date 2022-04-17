package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Following;
import com.vivacon.mapper.AccountMapper;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.FollowingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class FollowingServiceImpl implements FollowingService {

    private FollowingRepository followingRepository;

    private AccountMapper accountMapper;

    private AccountService accountService;

    public FollowingServiceImpl(FollowingRepository followingRepository,
                                AccountMapper accountMapper,
                                AccountService accountService) {
        this.followingRepository = followingRepository;
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }


    @Override
    public boolean follow(Long toAccountId) {
        Account fromAccount = accountService.getCurrentAccount();
        Account toAccount = accountService.getAccountById(toAccountId);

        Following following = new Following(fromAccount, toAccount);
        try {
            this.followingRepository.save(following);
        } catch (DataIntegrityViolationException e) {
            throw new NonUniqueResultException("The following table already have one record which contain this account follow that account");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean unfollow(Long toAccountId) {
        Account fromAccount = accountService.getCurrentAccount();
        Account toAccount = this.accountService.getAccountById(toAccountId);
        this.followingRepository.unfollowById(fromAccount.getId(), toAccount.getId());
        return true;
    }

    @Override
    public PageDTO<AccountResponse> findFollower(Long fromAccount, Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Account.class);
        Page<Account> listFollower = this.followingRepository.findFollower(accountService.getCurrentAccount().getId(), pageable);
        return PageDTOMapper.toPageDTO(listFollower, AccountResponse.class, account -> accountMapper.toResponse(account));
    }

    @Override
    public PageDTO<AccountResponse> findFollowing(Long fromAccount, Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Account.class);
        Page<Account> listFollower = this.followingRepository.findFollowing(accountService.getCurrentAccount().getId(), pageable);
        return PageDTOMapper.toPageDTO(listFollower, AccountResponse.class, account -> accountMapper.toResponse(account));
    }
}
