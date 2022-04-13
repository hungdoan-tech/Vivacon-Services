package com.vivacon.mapper;

import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.OutlineAccount;
import com.vivacon.entity.Account;
import com.vivacon.entity.Following;
import com.vivacon.repository.AttachmentRepository;
import com.vivacon.repository.FollowingRepository;
import com.vivacon.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    private AccountService accountService;

    private ModelMapper mapper;

    private FollowingRepository followingRepository;

    private AttachmentRepository attachmentRepository;

    public AccountMapper(ModelMapper mapper, FollowingRepository followingRepository, AttachmentRepository attachmentRepository) {
        this.mapper = mapper;
        this.followingRepository = followingRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public AccountResponse toResponse(Object object) {
        Account account = (Account) object;
        return this.mapper.map(account, AccountResponse.class);
    }

    public OutlineAccount toOutlineAccount(Object object) {
        try {
            Account account = (Account) object;
            OutlineAccount outlineAccountResponse = mapper.map(account, OutlineAccount.class);


            Optional<Following> following = followingRepository.findByIdComposition(accountService.getCurrentAccount().getId(), account.getId());
            //attachmentRepository.findBy

            outlineAccountResponse.setFollowing(following.isPresent());

            return outlineAccountResponse;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }
}
