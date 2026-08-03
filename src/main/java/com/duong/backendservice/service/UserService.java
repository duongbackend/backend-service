package com.duong.backendservice.service;

import com.duong.backendservice.dto.response.UserDetailResponse;

import java.util.List;

public interface UserService {
    List<UserDetailResponse> getAllUsers();

    UserDetailResponse getUserById(String id);
}
