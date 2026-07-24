package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.LoginResponse;
import com.duong.backendservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse data = authService.login(request);
        return ApiResponse.<LoginResponse>builder()
                .status("success")
                .data(data)
                .build();
    }
}
