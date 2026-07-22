package com.duong.backendservice.dto.response;

import lombok.Builder;

@Builder
public record CreateUserResponse(
        String id,
        String email,
        String name
) {
}
