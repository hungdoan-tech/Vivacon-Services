package com.vivacon.service.impl;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.entity.Account;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.security.UserDetailImpl;
import com.vivacon.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getCurrentAccount() {
        UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByUsernameIgnoreCase(principal.getUsername())
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public DetailProfile getProfileByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {

        return null;
    }

    @Override
    public AttachmentDTO changeProfileAvatar(AttachmentDTO avatar) {
        
        return null;
    }
}
