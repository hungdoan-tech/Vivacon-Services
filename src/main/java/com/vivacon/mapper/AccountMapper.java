package com.vivacon.mapper;

import com.vivacon.dto.response.AccountResponse;
import com.vivacon.entity.Account;
import com.vivacon.entity.Attachment;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.vivacon.common.constant.Constants.BLANK_AVATAR_URL;

@Component
public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    private ModelMapper mapper;

    private AttachmentRepository attachmentRepository;

    public AccountMapper(ModelMapper mapper, AttachmentRepository attachmentRepository) {
        this.mapper = mapper;
        this.attachmentRepository = attachmentRepository;
    }

    public AccountResponse toResponse(Object object) {
        Account account = (Account) object;
        AccountResponse responseAccount = this.mapper.map(account, AccountResponse.class);

        Optional<Attachment> avatar = attachmentRepository.findFirstByProfile_IdOrderByTimestampDesc(account.getId());
        String avatarUrl = avatar.isPresent() ? avatar.get().getUrl() : BLANK_AVATAR_URL;
        responseAccount.setAvatar(avatarUrl);
        return responseAccount;
    }
}
