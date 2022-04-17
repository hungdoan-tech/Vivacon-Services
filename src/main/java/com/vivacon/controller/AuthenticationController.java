package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.common.utility.JwtUtils;
import com.vivacon.dto.request.LoginRequest;
import com.vivacon.dto.request.RegistrationRequest;
import com.vivacon.dto.request.TokenRefreshRequest;
import com.vivacon.dto.response.AuthenticationResponse;
import com.vivacon.entity.Account;
import com.vivacon.exception.TokenRefreshException;
import com.vivacon.service.AccountService;
import com.vivacon.service.RefreshTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtils jwtTokenUtil,
                                    RefreshTokenService refreshTokenService,
                                    AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
        this.accountService = accountService;
    }

    /**
     * This endpoint is used to provide a username/password mechanism authentication
     *
     * @param loginRequest
     * @return ResponseDTO<LoginResponseDTO>
     */
    @ApiOperation(value = "Login to the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.LOGIN_SUCCESSFULLY),
            @ApiResponse(code = 401, message = Constants.BAD_CREDENTIALS)})
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails userDetail = (UserDetails) authenticate.getPrincipal();

        Account account = accountService.getAccountByUsernameIgnoreCase(userDetail.getUsername());
        List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String accessToken = jwtTokenUtils.generateAccessToken(account, roles);
        String refreshToken = refreshTokenService.createRefreshToken(userDetail.getUsername());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    /**
     * This endpoint is used to provide a way to get a new access token by proving a refresh token
     *
     * @param tokenRefreshRequest
     * @return ResponseDTO<TokenRefreshResponseDTO>
     */
    @ApiOperation(value = "Request a new access token by providing a refresh token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.RETURN_NEW_ACCESS_TOKEN),
            @ApiResponse(code = 401, message = Constants.REFRESH_TOKEN_NOT_STORE)})
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

    @ApiOperation(value = "Register new account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.RETURN_NEW_ACCESS_TOKEN),
            @ApiResponse(code = 401, message = Constants.REFRESH_TOKEN_NOT_STORE)})
    @PostMapping("/registration")
    public AuthenticationResponse registerNewAccount(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return accountService.registerNewAccount(registrationRequest);
    }

    @ApiOperation(value = "Check new unique username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.RETURN_NEW_ACCESS_TOKEN),
            @ApiResponse(code = 401, message = Constants.REFRESH_TOKEN_NOT_STORE)})
    @GetMapping("/check")
    public ResponseEntity<Object> checkUniqueUsername(@RequestParam(value = "username", required = false) Optional<String> username,
                                                      @RequestParam(value = "email", required = false) Optional<String> email) {
        boolean checkingResult = true;
        if (username.isPresent()) {
            if (!this.accountService.checkUniqueUsername(username.get())) {
                checkingResult = false;
            }
        }
        if (email.isPresent()) {
            if (!this.accountService.checkUniqueEmail(email.get())) {
                checkingResult = false;
            }
        }
        return checkingResult
                ? ResponseEntity.ok().body(null)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
