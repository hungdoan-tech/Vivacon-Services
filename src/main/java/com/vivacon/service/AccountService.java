package com.vivacon.service;

import com.vivacon.entity.Account;

public interface AccountService {
    Account getCurrentAccount();

    Account getAccountById(Long accountId);
}
