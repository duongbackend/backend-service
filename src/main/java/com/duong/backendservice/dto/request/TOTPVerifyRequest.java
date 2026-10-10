package com.duong.backendservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TOTPVerifyRequest(
        @NotBlank(message = "TOTP code is required")
        String code
) {
}
