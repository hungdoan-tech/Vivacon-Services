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

@Component
public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

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

    public AccountResponse toResponse(Object object) {
        Account account = (Account) object;
        return this.mapper.map(account, AccountResponse.class);
    }

    public OutlineAccount toOutlineAccount(Object object) {
        try {
            Account account = (Account) object;
            OutlineAccount outlineAccountResponse = mapper.map(account, OutlineAccount.class);

            UserDetailImpl principal = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account currentAccount = accountRepository.findByUsernameIgnoreCase(principal.getUsername())
                    .orElseThrow(RecordNotFoundException::new);

            Optional<Following> following = followingRepository.findByIdComposition(currentAccount.getId(), account.getId());
            Optional<Attachment> attachment = attachmentRepository.findFirstByProfile_IdOrderByTimestampDesc(account.getId());

            String avatarUrl = attachment.isPresent() ? attachment.get().getUrl() : "https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-13T21%3A17%3A26.245336500_Blank-Avatar.jpg";

            outlineAccountResponse.setFollowing(following.isPresent());
            outlineAccountResponse.setAvatar(avatarUrl);

            return outlineAccountResponse;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }
}
