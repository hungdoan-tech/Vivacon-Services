package com.vivacon.service.impl;


import com.vivacon.common.PageableBuilder;
import com.vivacon.dto.response.OutlineAccount;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Like;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.LikeRepository;
import com.vivacon.service.LikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }


    @Override
    public PageDTO<OutlineAccount> getAll(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex, Long postId) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Like.class);
        Page<Account> entityPage = likeRepository.findAllLikeByAccount(postId, pageable);
        return PageDTOMapper.toPageDTO(entityPage, OutlineAccount.class, entity -> );
    }

}
