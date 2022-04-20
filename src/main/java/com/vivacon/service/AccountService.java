package com.vivacon.service;

import com.vivacon.dto.AttachmentDTO;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.response.DetailProfile;
import com.vivacon.dto.response.OutlinePost;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;

import java.util.Optional;

public interface AccountService {

    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    AttachmentDTO changeProfileAvatar(AttachmentDTO avatar);

    PageDTO<AttachmentDTO> getProfileAvatarsByAccountId(Long accountId, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    Account getAccountByUsernameIgnoreCase(String username);

    DetailProfile getProfileByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    PageDTO<OutlinePost> getOutlinePostByUsername(String username, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    Account registerNewAccount(RegistrationRequest registrationRequest);

    boolean checkUniqueUsername(String username);

    boolean checkUniqueEmail(String email);

    Account verifyAccount(String verificationCode);

    Account resendVerificationToken(String email);

    Account changePassword(ForgotPasswordRequest forgotPasswordRequest);
}
