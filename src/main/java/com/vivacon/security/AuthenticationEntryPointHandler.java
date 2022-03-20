package com.vivacon.security;

import com.vivacon.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.vivacon.common.constant.Constants.ERROR_MESSAGE_ATTRIBUTE_HTTP_REQUEST;
import static com.vivacon.common.constant.Constants.JSON_CONTENT_TYPE;
import static com.vivacon.common.constant.Constants.OBJECT_NULL_CANT_CONVERT_TO_JSON;
import static com.vivacon.common.constant.Constants.UNAUTHORIZED_REASON;

/**
 * This class is used to catch all exceptions which extend from AuthenticationException through the filter pipeline of
 * Spring Security
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error(UNAUTHORIZED_REASON, authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(JSON_CONTENT_TYPE);
        ResponseDTO<Object> error = new ResponseDTO<>(HttpStatus.UNAUTHORIZED, request.getAttribute(ERROR_MESSAGE_ATTRIBUTE_HTTP_REQUEST).toString(), null);
        response.getWriter().write(convertObjectToJson(error));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        Assert.notNull(object, OBJECT_NULL_CANT_CONVERT_TO_JSON);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}