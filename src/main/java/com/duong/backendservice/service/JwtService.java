package com.duong.backendservice.service;

import com.duong.backendservice.entity.User;

public interface JwtService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
