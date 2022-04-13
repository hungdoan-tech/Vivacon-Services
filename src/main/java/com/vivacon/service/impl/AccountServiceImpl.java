package com.vivacon.service.impl;

import com.vivacon.common.PageableBuilder;
import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Following;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.mapper.PostMapper;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.security.UserDetailImpl;
import com.vivacon.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private AttachmentRepository attachmentRepository;

    private PostRepository postRepository;

    private FollowingRepository followingRepository;

    private PostMapper postMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AttachmentRepository attachmentRepository,
                              PostRepository postRepository,
                              FollowingRepository followingRepository,
                              PostMapper postMapper) {
        this.accountRepository = accountRepository;
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
        this.postMapper = postMapper;
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
        Attachment avatar = attachmentRepository.findFirstByProfile_IdOrderByTimestampDesc(accountId).orElse(null);

        Account profile = accountRepository.findById(accountId).orElseThrow(RecordNotFoundException::new);
        String blankAvatarUrl = "https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-13T21%3A17%3A26.245336500_Blank-Avatar.jpg";

        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorId(accountId, pageable);
        PageDTO<OutlinePost> listOutlinePost = PageDTOMapper.toPageDTO(pagePost, OutlinePost.class, entity -> this.postMapper.toOutlinePost(entity));

        Long postCounting = postRepository.getPostCountingByAccountId(profile.getId());
        Long followerCounting = followingRepository.getFollowerCountingByAccountId(profile.getId());
        Long followingCounting = followingRepository.getFollowingCountingByAccountId(profile.getId());

        long fromAccountId = getCurrentAccount().getId();
        long toAccountId = accountId;
        Optional<Following> following = this.followingRepository.findByIdComposition(fromAccountId, toAccountId);

        return new DetailProfile(profile, (avatar != null) ? avatar.getUrl() : blankAvatarUrl,
                postCounting, followerCounting, followingCounting, following.isPresent(), listOutlinePost);
    }

    @Override
    public AttachmentDTO changeProfileAvatar(AttachmentDTO avatarDto) {
        Attachment avatarEntity = new Attachment(avatarDto.getActualName(), avatarDto.getUniqueName(), avatarDto.getUrl(), getCurrentAccount());
        attachmentRepository.save(avatarEntity);
        return avatarDto;
    }

    @Override
    public PageDTO<OutlinePost> getOutlinePostByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Post.class);
        Page<Post> pagePost = postRepository.findByAuthorId(accountId, pageable);
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
