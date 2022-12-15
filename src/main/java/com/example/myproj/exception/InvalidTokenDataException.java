package com.example.myproj.exception;

import com.example.myproj.enums.ApiResponseCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class InvalidTokenDataException extends AuthenticationException {

    private ApiResponseCode apiResponseCode;

    public InvalidTokenDataException(ApiResponseCode apiResponseCode) {
        super(apiResponseCode.getDescription());
        this.apiResponseCode = apiResponseCode;
    }

    public InvalidTokenDataException(ApiResponseCode apiResponseCode, Throwable t) {
        super(apiResponseCode.getDescription(), t);
        this.apiResponseCode = apiResponseCode;
    }

}