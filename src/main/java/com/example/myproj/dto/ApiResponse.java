package com.example.myproj.dto;

import com.example.myproj.enums.ApiResponseCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;

@Getter
public class ApiResponse<T> implements Serializable {

    public static final ApiResponse<Void> EMPTY = new ApiResponse<>(null, null);

    private T data;
    private Error error;

    public ApiResponse() {
    }

    protected ApiResponse(T data, Error error) {
        this.data = data;
        this.error = error;
    }

    public static ApiResponse<Void> error(ApiResponseCode apiResponseCode, Object... params) {
        return new ApiResponse<>(null, new Error(apiResponseCode.getCode(), apiResponseCode.getDescription(params)));
    }

    public static ApiResponse<Void> error(ApiResponseCode apiResponseCode, Throwable throwable, Object... params) {
        return new ApiResponse<>(null, new Error(apiResponseCode.getCode(), apiResponseCode.getDescription(params), ExceptionUtils.getStackTrace(throwable)));
    }

    public static ApiResponse<Void> error(ApiResponseCode apiResponseCode, String serverMessage, Object... params) {
        return new ApiResponse<>(null, new Error(apiResponseCode.getCode(), apiResponseCode.getDescription(params), serverMessage));
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    @Getter
    protected static class Error {

        private int code;
        private String description;
        private String serverMessage;

        public Error() {
        }

        private Error(int code, String description, String serverMessage) {
            this.code = code;
            this.description = description;
            this.serverMessage = StringUtils.substring(serverMessage, 0, 2000);
        }

        private Error(int code, String description) {
            this.code = code;
            this.description = description;
            this.serverMessage = null;
        }
    }

}
