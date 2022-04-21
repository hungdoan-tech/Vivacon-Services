package com.vivacon.service.impl;

import com.vivacon.common.enum_type.RoleType;
import com.vivacon.dto.request.ChangePasswordRequest;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.entity.Account;
import com.vivacon.event.GeneratingVerificationTokenEvent;
import com.vivacon.event.RegistrationCompleteEvent;
import com.vivacon.exception.InvalidPasswordException;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.exception.VerificationTokenException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.RoleRepository;
import com.vivacon.security.UserDetailImpl;
import com.vivacon.service.AccountService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.time.Instant;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Account getCurrentAccount() {
        UserDetails principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByUsernameIgnoreCase(principal.getUsername())
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Account getAccountByUsernameIgnoreCase(String username) {
        return this.accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public boolean checkUniqueUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public boolean checkUniqueEmail(String email) {
        return accountRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Account registerNewAccount(RegistrationRequest registrationRequest) {
        try {
            Account account = new Account.AccountBuilder()
                    .fullName(registrationRequest.getFullName())
                    .username(registrationRequest.getUsername())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(roleRepository.findByName(RoleType.USER.toString()))
                    .active(false)
                    .build();
            Account savedAccount = accountRepository.saveAndFlush(account);
            applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(this, savedAccount));
            return savedAccount;
        } catch (DataIntegrityViolationException e) {
            throw new NonUniqueResultException("Some fields in the request body are already existing in our system");
        }
    }

    @Override
    @Transactional
    public Account verifyAccount(String verificationCode) {
        Optional<Account> account = accountRepository.findByVerificationToken(verificationCode);
        if (account.isPresent() && account.get().getVerificationExpiredDate().isAfter(Instant.now())) {
            accountRepository.activateByVerificationToken(verificationCode);
            return account.get();
        } else {
            throw new VerificationTokenException("Verification token was expired. Please make a new sign in request");
        }
    }

    @Override
    public Account resendVerificationToken(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            applicationEventPublisher.publishEvent(new GeneratingVerificationTokenEvent(this, account.get()));
            return account.get();
        } else {
            throw new RecordNotFoundException("The request verification token is invalid because of wrong email");
        }
    }

    @Override
    public Account forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<Account> account = accountRepository.findByVerificationToken(forgotPasswordRequest.getVerificationToken());
        if (account.isPresent() && account.get().getVerificationExpiredDate().isAfter(Instant.now())) {
            if (passwordEncoder.matches(forgotPasswordRequest.getOldPassword(), account.get().getPassword())) {
                account.get().setPassword(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()));
                return accountRepository.saveAndFlush(account.get());
            } else {
                throw new InvalidPasswordException();
            }
        } else {
            throw new VerificationTokenException();
        }
    }

    @Override
    public Account changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = getCurrentAccount();
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            return accountRepository.saveAndFlush(account);
        } else {
            throw new InvalidPasswordException();
        }
    }
}
