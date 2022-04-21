package com.vivacon.service;

import com.vivacon.dto.request.ChangePasswordRequest;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.entity.Account;

public interface AccountService {

    boolean checkUniqueUsername(String username);

    boolean checkUniqueEmail(String email);

    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    Account getAccountByUsernameIgnoreCase(String username);

    Account registerNewAccount(RegistrationRequest registrationRequest);

    Account verifyAccount(String verificationCode);

    Account resendVerificationToken(String email);

    Account forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    Account changePassword(ChangePasswordRequest changePasswordRequest);
}
