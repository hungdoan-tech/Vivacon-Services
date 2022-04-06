package com.vivacon.service.impl;

import com.vivacon.entity.Account;
import com.vivacon.entity.Following;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.FollowingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FollowingServiceImpl implements FollowingService {

    private FollowingRepository followingRepository;

    private AccountService accountService;

    public FollowingServiceImpl(FollowingRepository followingRepository,
                                AccountService accountService) {
        this.followingRepository = followingRepository;
        this.accountService = accountService;
    }


    @Override
    public boolean follow(Long toAccountId) {
        Account fromAccount = accountService.getCurrentAccount();
        Account toAccount = accountService.getAccountById(toAccountId);

        if (followingRepository.findByFromAccountAndAndToAccount(fromAccount, toAccount).isPresent()) {
            throw new NonUniqueResultException("The following table already have one record which contain this account follow that account");
        }
        Following following = new Following(fromAccount, toAccount);
        this.followingRepository.save(following);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean unfollow(Long toAccountId) {
        Account fromAccount = accountService.getCurrentAccount();
        Account toAccount = this.accountService.getAccountById(toAccountId);

        if (followingRepository.findByIdComposition(fromAccount.getId(), toAccount.getId()).isEmpty()) {
            throw new RecordNotFoundException("The following table doesnt have any record which contain this account follow that account");
        }
        this.followingRepository.unfollowById(fromAccount.getId(), toAccount.getId());
        return true;
    }

    @Override
    public List<Account> findFollower(Optional<Long> account) {
        return null;
    }

    @Override
    public List<Account> findFollowing(Optional<Long> account) {
        return null;
    }
}
