package com.duong.backendservice.dto.response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String status,
        String message,
        T data
) {
}