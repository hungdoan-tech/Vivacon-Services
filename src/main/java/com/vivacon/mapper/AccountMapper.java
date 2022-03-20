package com.vivacon.mapper;

import com.vivacon.dto.response.AccountResponse;
import com.vivacon.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private ModelMapper mapper;

    public AccountMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public AccountResponse convertToDto(Account account) {
        return this.mapper.map(account, AccountResponse.class);
    }
}
