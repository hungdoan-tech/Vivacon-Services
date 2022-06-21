package com.vivacon.service.impl;

import com.vivacon.common.enum_type.RoleType;
import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.AdminRegistrationRequest;
import com.vivacon.dto.response.AccountAdminResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.AccountAdminMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.RoleRepository;
import com.vivacon.service.AdminService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private AccountRepository accountRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AccountAdminMapper accountAdminMapper;

    public AdminServiceImpl(AccountRepository accountRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            AccountAdminMapper accountAdminMapper) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountAdminMapper = accountAdminMapper;
    }

    @Override
    public PageDTO<AccountAdminResponse> getAll(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, AccountAdminResponse.class);
        Page<Account> accountAdminResponses = accountRepository.findAll(pageable);
        return PageMapper.toPageDTO(accountAdminResponses, account -> accountAdminMapper.toUserAccountMostFollower(account));
    }

    @Override
    public Account registerNewAccount(AdminRegistrationRequest registrationRequest) {
        try {
            Account account = new Account.AccountBuilder()
                    .fullName(registrationRequest.getFullName())
                    .username(registrationRequest.getUsername())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(roleRepository.findByName(RoleType.ADMIN.toString()))
                    .active(true)
                    .build();
            Account savedAccount = accountRepository.saveAndFlush(account);
            return savedAccount;
        } catch (DataIntegrityViolationException e) {
            throw new NonUniqueResultException("Some fields in the request body are already existing in our system");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public boolean deleteAccount(Long id) {
        Account account = accountRepository.findByIdAndActive(id, true).orElseThrow(RecordNotFoundException::new);
        this.accountRepository.deleteById(account.getId());
        return true;
    }
}
