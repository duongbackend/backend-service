package com.duong.backendservice.service;

import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.LoginResponse;

public interface AuthService {
    CreateUserResponse register(CreateUserRequest request);

    LoginResponse login(LoginRequest request);
}
