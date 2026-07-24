package com.duong.backendservice.dto.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record LoginResponse(
        String userId,
        String accessToken,
        String refreshToken,
        Set<String> authorities
) {
}
