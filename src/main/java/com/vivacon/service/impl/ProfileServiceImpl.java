package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.EditProfileInformationRequest;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Following;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.PageMapper;
import com.vivacon.mapper.PostMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.AccountService;
import com.vivacon.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.BLANK_AVATAR_URL;

@Service
public class ProfileServiceImpl implements ProfileService {

    private AccountService accountService;

    private AttachmentRepository attachmentRepository;

    private PostRepository postRepository;

    private FollowingRepository followingRepository;

    private AccountRepository accountRepository;

    private PostMapper postMapper;

    public ProfileServiceImpl(AccountService accountService,
                              AttachmentRepository attachmentRepository,
                              PostRepository postRepository,
                              FollowingRepository followingRepository,
                              AccountRepository accountRepository,
                              PostMapper postMapper) {
        this.accountService = accountService;
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
        this.accountRepository = accountRepository;
        this.postMapper = postMapper;
    }

    @Override
    public DetailProfile getProfileByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Account requestAccount = this.accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new);
        return this.getProfile(requestAccount, order, sort, pageSize, pageIndex);
    }

    @Override
    public AttachmentDTO changeProfileAvatar(AttachmentDTO avatarDto) {
        Attachment avatarEntity = new Attachment(avatarDto.getActualName(), avatarDto.getUniqueName(), avatarDto.getUrl(), accountService.getCurrentAccount());
        attachmentRepository.save(avatarEntity);
        return avatarDto;
    }

    @Override
    public PageDTO<AttachmentDTO> getProfileAvatarsByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Attachment.class);
        Page<Attachment> avatarPage = attachmentRepository.findByProfileId(accountId, pageable);
        return PageMapper.toPageDTO(avatarPage, attachment -> new AttachmentDTO(attachment));
    }

    @Override
    public PageDTO<OutlinePost> getOutlinePostByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Account requestAccount = this.accountRepository.findByUsernameIgnoreCase(username).orElseThrow(RecordNotFoundException::new);
        return this.getOutlinePost(requestAccount, order, sort, pageSize, pageIndex);
    }


    private DetailProfile getProfile(Account requestAccount, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Optional<Attachment> avatar = attachmentRepository.findFirstByProfileIdOrderByTimestampDesc(requestAccount.getId());
        String avatarUrl = avatar.isPresent() ? avatar.get().getUrl() : BLANK_AVATAR_URL;
        Account profile = accountRepository.findById(requestAccount.getId()).orElseThrow(RecordNotFoundException::new);

        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorIdAndActive(requestAccount.getId(), true, pageable);
        PageDTO<OutlinePost> listOutlinePost = PageMapper.toPageDTO(pagePost, post -> this.postMapper.toOutlinePost(post));

        Long postCounting = postRepository.getPostCountingByAccountId(profile.getId());
        Long followerCounting = followingRepository.getFollowerCountingByAccountId(profile.getId());
        Long followingCounting = followingRepository.getFollowingCountingByAccountId(profile.getId());

        long fromAccountId = accountService.getCurrentAccount().getId();
        long toAccountId = requestAccount.getId();
        Optional<Following> following = this.followingRepository.findByIdComposition(fromAccountId, toAccountId);

        return new DetailProfile(profile, avatarUrl, postCounting, followerCounting, followingCounting, following.isPresent(), listOutlinePost);
    }

    private PageDTO<OutlinePost> getOutlinePost(Account requestAccount, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorIdAndActive(requestAccount.getId(), true, pageable);
        return PageMapper.toPageDTO(pagePost, post -> this.postMapper.toOutlinePost(post));
    }

    @Override
    public Account editProfileInformation(EditProfileInformationRequest editProfileInformationRequest) {
        Account accountByEmail = this.accountRepository.findByEmail(editProfileInformationRequest.getEmail().trim()).orElseThrow(RecordNotFoundException::new);
        Account updatedAccount = new Account(accountByEmail.getId(), accountByEmail.getUsername(), accountByEmail.getEmail(), accountByEmail.getPassword(), editProfileInformationRequest.getFullName(), accountByEmail.getRole(), editProfileInformationRequest.getBio(), accountByEmail.getRefreshToken(), accountByEmail.getTokenExpiredDate(), accountByEmail.getVerificationToken(), accountByEmail.getVerificationExpiredDate(), editProfileInformationRequest.getPhoneNumber(), editProfileInformationRequest.getGender(), accountByEmail.getCreatedBy(), accountByEmail.getCreatedAt(), accountByEmail.getLastModifiedBy(), accountByEmail.getActive());
        return this.accountRepository.save(updatedAccount);
    }
}
