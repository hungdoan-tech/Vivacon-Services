package com.vivacon.service;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.entity.Account;

import java.util.Optional;

public interface AccountService {
    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    DetailProfile getProfileByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    AttachmentDTO changeProfileAvatar(AttachmentDTO avatar);
}
