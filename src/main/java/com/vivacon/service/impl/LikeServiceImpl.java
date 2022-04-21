package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.response.OutlineAccount;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Like;
import com.vivacon.entity.Post;
import com.vivacon.mapper.AccountMapper;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.LikeRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.LikeService;
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
public class LikeServiceImpl implements LikeService {

    private AccountMapper accountMapper;

    private LikeRepository likeRepository;

    private AccountService accountService;

    private PostRepository postRepository;

    public LikeServiceImpl(AccountMapper accountMapper, LikeRepository likeRepository, AccountService accountService, PostRepository postRepository) {
        this.accountMapper = accountMapper;
        this.likeRepository = likeRepository;
        this.accountService = accountService;
        this.postRepository = postRepository;
    }

    @Override
    public boolean like(Long postId) {
        Account currentAccount = accountService.getCurrentAccount();
        Post currentPost = postRepository.findByPostId(postId);
        Like liking = new Like(currentAccount, currentPost);
        try {
            this.likeRepository.save(liking);
        } catch (DataIntegrityViolationException e) {
            throw new NonUniqueResultException("The liking table already have one record which contain this account had liked this post before");
        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean unlike(Long postId) {
        Account currentAccount = accountService.getCurrentAccount();
        this.likeRepository.unlikeById(currentAccount.getId(), postId);
        return true;
    }

    @Override
    public PageDTO<OutlineAccount> getAll(Long postId, Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Like.class);
        Page<Account> entityPage = likeRepository.findAllLikeByPostId(postId, pageable);
        return PageDTOMapper.toPageDTO(entityPage, OutlineAccount.class, entity -> this.accountMapper.toOutlineAccount(entity));
    }
}
