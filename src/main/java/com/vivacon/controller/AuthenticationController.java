package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.common.utility.JwtUtils;
import com.vivacon.common.validation.UniqueEmail;
import com.vivacon.dto.request.ChangePasswordRequest;
import com.vivacon.dto.request.ForgotPasswordRequest;
import com.vivacon.dto.request.LoginRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.request.TokenRefreshRequest;
import com.vivacon.dto.response.AccountResponse;
import com.vivacon.dto.response.AuthenticationResponse;
import com.vivacon.entity.Account;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.exception.TokenRefreshException;
import com.vivacon.service.AccountService;
import com.vivacon.service.RefreshTokenService;
import com.vivacon.service.impl.DeviceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Authentication Controller")
@RestController
@RequestMapping(value = Constants.API_V1)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtTokenUtils;

    private RefreshTokenService refreshTokenService;

    private AccountService accountService;

    private DeviceServiceImpl deviceService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtils jwtTokenUtil,
                                    RefreshTokenService refreshTokenService,
                                    AccountService accountService,
                                    DeviceServiceImpl deviceService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
        this.accountService = accountService;
        this.deviceService = deviceService;
    }

    private AuthenticationResponse generateAuthenticationResponse(String username, List<String> authorities) {
        Account account = accountService.getAccountByUsernameIgnoreCase(username);
        List<String> roles = authorities;

        String accessToken = jwtTokenUtils.generateAccessToken(account, roles);
        String refreshToken = refreshTokenService.createRefreshToken(username);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    /**
     * This endpoint is used to provide a username/password mechanism authentication
     *
     * @param loginRequest
     * @return ResponseDTO<LoginResponseDTO>
     */
    @ApiOperation(value = "Login to the system")
    @PostMapping("/login")
    public AuthenticationResponse login(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        @Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetail = (UserDetails) authenticate.getPrincipal();
        Account account = accountService.getAccountByUsernameIgnoreCase(userDetail.getUsername());
        deviceService.verifyDevice(account, request);
        return generateAuthenticationResponse(userDetail.getUsername(),
                userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    /**
     * This endpoint is used to provide a way to get a new access token by proving a refresh token
     *
     * @param tokenRefreshRequest
     * @return ResponseDTO<TokenRefreshResponseDTO>
     */
    @ApiOperation(value = "Request a new access token by providing a refresh token")
    @PostMapping("/refresh-token")
    public AuthenticationResponse generateNewAccessTokenByRefreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService
                .findAccountByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::verifyTokenExpiration)
                .map(account -> {
                    List<String> roles = Arrays.asList(account.getRole().getName());
                    String newAccessToken = jwtTokenUtils.generateAccessToken(account, roles);
                    return new AuthenticationResponse(newAccessToken, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, Constants.REFRESH_TOKEN_NOT_STORE));
    }

    @ApiOperation(value = "Check unique username/email")
    @GetMapping("/account/check")
    public AccountResponse checkUniqueUsername(@RequestParam(value = "username", required = false) Optional<String> username,
                                               @RequestParam(value = "email", required = false) Optional<String> email) {
        if (username.isPresent()) {
            return accountService.checkUniqueUsername(username.get());
        }
        if (email.isPresent()) {
            return accountService.checkUniqueEmail(email.get());
        }
        throw new RecordNotFoundException("Bad checking data request - no account match your request");
    }

    @ApiOperation(value = "Register new account")
    @PostMapping("/registration")
    public ResponseEntity<Object> registerNewAccount(@Valid @RequestBody RegistrationRequest registrationRequest) {
        accountService.registerNewAccount(registrationRequest);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "Active new account by verification token")
    @PostMapping("/account/active")
    public AuthenticationResponse activeAccount(@NotEmpty @RequestBody String code) {
        Account account = accountService.activeAccount(code);
        return generateAuthenticationResponse(account.getUsername(), Arrays.asList(account.getRole().toString()));
    }

    @ApiOperation(value = "Active new account by verification token")
    @PostMapping("/account/verify")
    public ResponseEntity<Object> verifyAccount(@NotEmpty @RequestBody String code) {
        accountService.verifyAccount(code);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Resend verification token")
    @PostMapping("/account/verification_token")
    public ResponseEntity<Object> resendVerificationToken(@Email @UniqueEmail @RequestBody String email) {
        accountService.resendVerificationToken(email);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "Forgot password")
    @PostMapping("/account/password")
    public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        accountService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "Change authenticated account password")
    @PutMapping("/account/password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        accountService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().body(null);
    }
}
