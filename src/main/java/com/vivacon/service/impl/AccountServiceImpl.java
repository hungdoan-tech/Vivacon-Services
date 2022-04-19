package com.vivacon.service.impl;

import com.vivacon.common.enum_type.RoleType;
import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Following;
import com.vivacon.entity.Post;
import com.vivacon.event.GeneratingVerificationTokenEvent;
import com.vivacon.event.RegistrationCompleteEvent;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.exception.VerificationTokenException;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.mapper.PostMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.repository.RoleRepository;
import com.vivacon.security.UserDetailImpl;
import com.vivacon.service.AccountService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.time.Instant;
import java.util.Optional;

import static com.vivacon.common.constant.Constants.BLANK_AVATAR_URL;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private AttachmentRepository attachmentRepository;

    private PostRepository postRepository;

    private FollowingRepository followingRepository;

    private RoleRepository roleRepository;

    private PostMapper postMapper;

    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AttachmentRepository attachmentRepository,
                              PostRepository postRepository,
                              FollowingRepository followingRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder,
                              PostMapper postMapper,
                              ApplicationEventPublisher applicationEventPublisher) {
        this.accountRepository = accountRepository;
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
        this.roleRepository = roleRepository;
        this.postMapper = postMapper;
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
    @Transactional
    public Account verifyAccount(String verificationCode) {
        Optional<Account> account = accountRepository.findByVerificationToken(verificationCode);
        if (account.isPresent() && account.get().getVerificationExpiredDate().isAfter(Instant.now())) {
            accountRepository.activateByVerificationToken(verificationCode);
            return account.get();
        } else {
            throw new VerificationTokenException(account.get().getRefreshToken(), "Verification token was expired. Please make a new sign in request");
        }
    }

    @Override
    public Account resendVerificationToken(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent() && account.get().getActive() == false) {
            applicationEventPublisher.publishEvent(new GeneratingVerificationTokenEvent(this, account.get()));
            return account.get();
        } else {
            throw new RecordNotFoundException("The request verification token is invalid because of wrong email");
        }
    }

    @Override
    public DetailProfile getProfileByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Account requestAccount = this.accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new);
        return this.getProfile(requestAccount, order, sort, pageSize, pageIndex);
    }

    private DetailProfile getProfile(Account requestAccount, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Optional<Attachment> avatar = attachmentRepository.findFirstByProfile_IdOrderByTimestampDesc(requestAccount.getId());
        String avatarUrl = avatar.isPresent() ? avatar.get().getUrl() : BLANK_AVATAR_URL;
        Account profile = accountRepository.findById(requestAccount.getId()).orElseThrow(RecordNotFoundException::new);

        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorId(requestAccount.getId(), pageable);
        PageDTO<OutlinePost> listOutlinePost = PageDTOMapper.toPageDTO(pagePost, OutlinePost.class, entity -> this.postMapper.toOutlinePost(entity));

        Long postCounting = postRepository.getPostCountingByAccountId(profile.getId());
        Long followerCounting = followingRepository.getFollowerCountingByAccountId(profile.getId());
        Long followingCounting = followingRepository.getFollowingCountingByAccountId(profile.getId());

        long fromAccountId = getCurrentAccount().getId();
        long toAccountId = requestAccount.getId();
        Optional<Following> following = this.followingRepository.findByIdComposition(fromAccountId, toAccountId);

        return new DetailProfile(profile, avatarUrl, postCounting, followerCounting, followingCounting, following.isPresent(), listOutlinePost);
    }

    @Override
    public AttachmentDTO changeProfileAvatar(AttachmentDTO avatarDto) {
        Attachment avatarEntity = new Attachment(avatarDto.getActualName(), avatarDto.getUniqueName(), avatarDto.getUrl(), getCurrentAccount());
        attachmentRepository.save(avatarEntity);
        return avatarDto;
    }

    @Override
    public PageDTO<OutlinePost> getOutlinePostByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Account requestAccount = this.accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new);
        return this.getOutlinePost(requestAccount, order, sort, pageSize, pageIndex);
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

    private PageDTO<OutlinePost> getOutlinePost(Account requestAccount, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorId(requestAccount.getId(), pageable);
        PageDTO<OutlinePost> listOutlinePost = PageDTOMapper.toPageDTO(pagePost, OutlinePost.class, post -> this.postMapper.toOutlinePost(post));
        return listOutlinePost;
    }

    @Override
    public PageDTO<AttachmentDTO> getProfileAvatarsByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Attachment.class);
        Page<Attachment> pageAvatar = attachmentRepository.findByProfile_Id(accountId, pageable);
        PageDTO<AttachmentDTO> listAvatarDto = PageDTOMapper.toPageDTO(pageAvatar, AttachmentDTO.class, attachment -> new AttachmentDTO((Attachment) attachment));
        return listAvatarDto;
    }
}
