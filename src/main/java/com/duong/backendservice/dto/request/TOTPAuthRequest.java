package com.duong.backendservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TOTPAuthRequest(
        @NotBlank(message = "User ID is required")
        String userId,

        @NotBlank(message = "TOTP code is required")
        String code
) {
}
