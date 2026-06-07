package com.hintro.meetingintelligence.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String traceId;

    private boolean success;

    private T data;

    private ApiError error;

    public static <T> ApiResponse<T> success(
            String traceId,
            T data
    ) {

        ApiResponse<T> response =
                new ApiResponse<>();

        response.setTraceId(traceId);
        response.setSuccess(true);
        response.setData(data);

        return response;
    }

    public static <T> ApiResponse<T> failure(String traceId, String code, String message) {

        ApiResponse<T> response = new ApiResponse<>();

        response.setTraceId(traceId);
        response.setSuccess(false);

        response.setError(new ApiError(code, message));

        return response;
    }
}