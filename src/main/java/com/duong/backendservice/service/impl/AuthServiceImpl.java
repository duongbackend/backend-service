package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.UserStatus;
import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.UserDetailResponse;
import com.duong.backendservice.entity.User;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.mapper.UserMapper;
import com.duong.backendservice.repository.UserRepository;
import com.duong.backendservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTH-SERVICE")
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse register(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserStatus(UserStatus.ACTIVE);

        userRepository.save(user);
        return userMapper.toCreateUserResponse(user);
    }

    @Override
    public UserDetailResponse login(LoginRequest request) {
        log.info("Login with email: {}", request.email());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            User user = (User) authentication.getPrincipal();

            return UserDetailResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .avatarUrl(user.getAvatarUrl())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .userStatus(user.getUserStatus())
                    .build();
        } catch (AuthenticationException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
