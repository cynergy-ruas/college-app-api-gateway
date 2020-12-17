package io.github.cynergy.apigateway.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
    int code;
    String message;

    public ApiError(@JsonProperty("code") int code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiError unauthorizedError() {
        return new ApiError(401, "Access token is missing, invalid or user is not authorized to make the request.");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
