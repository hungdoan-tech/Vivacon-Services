package com.vivacon.service.impl;

import com.vivacon.dto.response.StatisticDataQuantity;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.StatisticService;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

    private PostRepository postRepository;

    private AccountRepository accountRepository;

    public StatisticServiceImpl(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public StatisticDataQuantity getStatisticData() {
        return new StatisticDataQuantity(postRepository.getAllPostCounting(), accountRepository.getAllAccountCounting());
    }
}
