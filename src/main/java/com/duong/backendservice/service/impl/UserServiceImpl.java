package com.duong.backendservice.service.impl;

import com.duong.backendservice.dto.response.UserDetailResponse;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.mapper.UserMapper;
import com.duong.backendservice.repository.UserRepository;
import com.duong.backendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.duong.backendservice.configuration.RedisConfiguration.USER_INFO_CACHE;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER-SERVICE")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<UserDetailResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDetailResponse)
                .toList();
    }

    @Cacheable(value = USER_INFO_CACHE, key = "#id")
    @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.name")
    @Override
    public UserDetailResponse getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDetailResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}