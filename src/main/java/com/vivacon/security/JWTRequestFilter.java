package com.vivacon.security;

import com.vivacon.common.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.vivacon.common.constant.Constants.ACCESS_TOKEN_MISSING;
import static com.vivacon.common.constant.Constants.AUTHORIZATION_BEARER;
import static com.vivacon.common.constant.Constants.AUTHORIZATION_HEADER;
import static com.vivacon.common.constant.Constants.CAN_NOT_SET_AUTHENTICATION_VALUE;
import static com.vivacon.common.constant.Constants.ERROR_MESSAGE_ATTRIBUTE_HTTP_REQUEST;
import static com.vivacon.common.constant.Constants.URL_WHITELIST;
import static com.vivacon.common.constant.Constants.WHITELIST_URL_REGEX;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    private UserDetailsService userDetailsService;

    @Autowired
    public JWTRequestFilter(JwtUtils jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is used to take jwt access token and decide is it valid or not to store in security context holder
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (WHITELIST_URL_REGEX.stream().noneMatch(url -> requestURI.matches(url)) &&
                URL_WHITELIST.stream().noneMatch(url -> url.equals(requestURI))) {
            try {
                String token = getTokenFromRequest(request);
                if (Objects.nonNull(token)) {
                    if (jwtUtils.validate(token)) {
                        String username = jwtUtils.getUsername(token);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    throw new JwtException(ACCESS_TOKEN_MISSING);
                }
            } catch (Exception ex) {
                logger.error(CAN_NOT_SET_AUTHENTICATION_VALUE, ex);
                request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_HTTP_REQUEST, ex.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method is used to get jwt value from the authorization http header
     *
     * @param request
     * @return
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(AUTHORIZATION_BEARER)) {
            return token.substring(AUTHORIZATION_BEARER.length());
        }
        return null;
    }
}
