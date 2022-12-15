package com.example.myproj.security.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.myproj.dto.ApiResponse;
import com.example.myproj.enums.ApiResponseCode;
import com.example.myproj.exception.InvalidTokenDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.error("Authentication failed.", authException);
        ApiResponseCode responseCode = getResponseCode(authException);
        IOUtils.write(objectMapper.writeValueAsString(ApiResponse.error(responseCode, authException)), response.getOutputStream(), StandardCharsets.UTF_8);
    }

    private static ApiResponseCode getResponseCode(AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            return ApiResponseCode.BAD_CREDENTIALS;
        } else if (authException instanceof InvalidTokenDataException) {
            return ((InvalidTokenDataException) authException).getApiResponseCode();
        } else {
            return ApiResponseCode.ACCESS_DENIED;
        }
    }

}
