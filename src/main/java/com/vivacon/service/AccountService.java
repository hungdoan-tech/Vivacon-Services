package com.vivacon.service;

import com.vivacon.dto.request.ChangePasswordRequest;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.entity.Account;

public interface AccountService {

    AccountResponse checkUniqueUsername(String username);

    AccountResponse checkUniqueEmail(String email);

    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    Account getAccountByUsernameIgnoreCase(String username);

    Account registerNewAccount(RegistrationRequest registrationRequest);

    Account activeAccount(String verificationCode);

    Account resendVerificationToken(String email);

    Account forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    Account changePassword(ChangePasswordRequest changePasswordRequest);

    Account verifyAccount(String code);
}
