package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.TOTPVerifyRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.service.TOTPService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/totp")
@RequiredArgsConstructor
public class TOTPController {
    private final TOTPService totpService;

    @PostMapping("/generate")
    public ApiResponse<String> generateQrCode(@AuthenticationPrincipal Jwt jwt) throws QrGenerationException {
        String userId = jwt.getSubject();
        String data = totpService.generateQrCode(userId);

        return ApiResponse.<String>builder()
                .status("success")
                .data(data)
                .message("QR code generated successfully")
                .build();
    }

    @PostMapping("/verify-code")
    public ApiResponse<Boolean> verifyCode(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid TOTPVerifyRequest request) {
        String userId = jwt.getSubject();
        boolean data = totpService.verifyCode(userId, request.code());
        return ApiResponse.<Boolean>builder()
                .status("success")
                .data(data)
                .message("Code verified successfully")
                .build();
    }
}
