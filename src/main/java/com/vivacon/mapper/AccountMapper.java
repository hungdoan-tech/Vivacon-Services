package com.vivacon.mapper;

import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.OutlineAccount;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.entity.Following;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.repository.AccountRepository;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.security.UserDetailImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.BLANK_AVATAR_URL;

@Component
public class AccountMapper {
    private ModelMapper mapper;

    private FollowingRepository followingRepository;

    private AttachmentRepository attachmentRepository;

    private AccountRepository accountRepository;

    public AccountMapper(ModelMapper mapper, FollowingRepository followingRepository, AttachmentRepository attachmentRepository, AccountRepository accountRepository) {
        this.mapper = mapper;
        this.followingRepository = followingRepository;
        this.attachmentRepository = attachmentRepository;
        this.accountRepository = accountRepository;
    }

    public AccountResponse toResponse(Account principal, Account account) {
        AccountResponse responseAccount = this.mapper.map(account, AccountResponse.class);

        Optional<Attachment> avatar = attachmentRepository.findFirstByProfileIdOrderByTimestampDesc(account.getId());
        String avatarUrl = avatar.isPresent() ? avatar.get().getUrl() : BLANK_AVATAR_URL;
        responseAccount.setAvatar(avatarUrl);

        if (principal != null) {
            Optional<Following> following = followingRepository.findByIdComposition(principal.getId(), account.getId());
            responseAccount.setFollowing(following.isPresent());
        }

        return responseAccount;
    }

    public OutlineAccount toOutlineAccount(Account account) {
        OutlineAccount outlineAccountResponse = mapper.map(account, OutlineAccount.class);

        UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account currentAccount = accountRepository.findByUsernameIgnoreCase(principal.getUsername())
                .orElseThrow(RecordNotFoundException::new);

        Optional<Following> following = followingRepository.findByIdComposition(currentAccount.getId(), account.getId());
        Optional<Attachment> attachment = attachmentRepository.findFirstByProfileIdOrderByTimestampDesc(account.getId());

        String avatarUrl = attachment.isPresent() ? attachment.get().getUrl() : BLANK_AVATAR_URL;

        outlineAccountResponse.setIsFollowing(following.isPresent());
        outlineAccountResponse.setAvatar(avatarUrl);

        return outlineAccountResponse;
    }
}
