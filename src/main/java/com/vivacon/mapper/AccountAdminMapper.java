package com.vivacon.mapper;

import com.vivacon.dto.response.AccountAdminResponse;
import com.vivacon.entity.Account;
import com.vivacon.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountAdminMapper {

    private ModelMapper mapper;

    private AttachmentRepository attachmentRepository;

    public AccountAdminMapper(ModelMapper mapper, AttachmentRepository attachmentRepository) {
        this.mapper = mapper;
        this.attachmentRepository = attachmentRepository;
    }

    public AccountAdminResponse toUserAccountMostFollower(Account account) {
        AccountAdminResponse response = mapper.map(account, AccountAdminResponse.class);
        return response;
    }
}
