package com.vivacon.service;

import com.vivacon.dto.request.ChangePasswordRequest;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.EssentialAccount;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Account;

import java.util.Optional;

public interface AccountService {

    AccountResponse checkUniqueUsername(String username);

    AccountResponse checkUniqueEmail(String email);

    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    Account getAccountByUsernameIgnoreCase(String username);

    Account registerNewAccount(RegistrationRequest registrationRequest);

    Account verifyAccount(String verificationCode);

    Account resendVerificationToken(String email);

    Account forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    Account changePassword(ChangePasswordRequest changePasswordRequest);

    PageDTO<EssentialAccount> findByName(String name, Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);
}
