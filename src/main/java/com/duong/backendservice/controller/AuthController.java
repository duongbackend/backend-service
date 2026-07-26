package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.LoginResponse;
import com.duong.backendservice.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ApiResponse<CreateUserResponse> register(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse data = authService.register(request);
        return ApiResponse.<CreateUserResponse>builder()
                .status("success")
                .message("User created successfully")
                .data(data)
                .build();
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response){
        LoginResponse data = authService.login(request);
        String refreshToken = data.getRefreshToken();

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(14))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        data.setRefreshToken(null);

        return ApiResponse.<LoginResponse>builder()
                .status("success")
                .data(data)
                .build();
    }
}
