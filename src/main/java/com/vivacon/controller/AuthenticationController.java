package com.vivacon.controller;

import com.vivacon.common.JwtUtils;
import com.vivacon.dto.ResponseDTO;
import com.vivacon.dto.request.LoginRequest;
import com.vivacon.dto.request.TokenRefreshRequest;
import com.vivacon.dto.response.AuthenticationResponse;
import com.vivacon.dto.response.TokenRefreshResponse;
import com.vivacon.exception.TokenRefreshException;
import com.vivacon.service.RefreshTokenService;
import com.vivacon.common.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Authentication Controller")
@RestController
@RequestMapping(value = Constants.API_V1)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtTokenUtils;

    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtils jwtTokenUtil,
                                    RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtil;
        this.refreshTokenService = refreshTokenService;
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
    public ResponseDTO<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails userDetail = (UserDetails) authenticate.getPrincipal();
        String accessToken = jwtTokenUtils.generateAccessToken(userDetail.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(userDetail.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(userDetail, accessToken, refreshToken);
        return new ResponseDTO<>(HttpStatus.OK, Constants.LOGIN_SUCCESSFULLY, authenticationResponse);
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
    public ResponseDTO<TokenRefreshResponse> generateNewAccessTokenByRefreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService
                .findAccountByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::verifyTokenExpiration)
                .map(account -> {
                    String newAccessToken = jwtTokenUtils.generateAccessToken(account.getUsername());
                    return new ResponseDTO<>(
                            HttpStatus.OK,
                            Constants.RETURN_NEW_ACCESS_TOKEN,
                            new TokenRefreshResponse(newAccessToken, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, Constants.REFRESH_TOKEN_NOT_STORE));
    }
}
