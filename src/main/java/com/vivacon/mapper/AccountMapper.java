package com.vivacon.mapper;

import com.vivacon.common.AuditableHelper;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.entity.Account;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    private ModelMapper mapper;

    private AuditableHelper auditableHelper;

    public AccountMapper(ModelMapper mapper,
                         AuditableHelper auditableHelper) {
        this.mapper = mapper;
        this.auditableHelper = auditableHelper;
    }

    public AccountResponse toResponse(Object object) {
        Account account = (Account) object;
        return this.mapper.map(account, AccountResponse.class);
    }

    public Account toEntity(AccountResponse accountResponse) {
        Account account = this.mapper.map(accountResponse, Account.class);
        return (Account) auditableHelper.updateAuditingCreatedFields(account, null);
    }
}
