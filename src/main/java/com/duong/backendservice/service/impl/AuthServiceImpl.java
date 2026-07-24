package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.UserStatus;
import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.LoginResponse;
import com.duong.backendservice.entity.User;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.mapper.UserMapper;
import com.duong.backendservice.repository.UserRepository;
import com.duong.backendservice.service.AuthService;
import com.duong.backendservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTH-SERVICE")
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
    public LoginResponse login(LoginRequest request) {
        log.info("Login with email: {}", request.email());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            User user = (User) authentication.getPrincipal();
            if(user == null){
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            Set<String> authorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            return LoginResponse.builder()
                    .userId(user.getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .authorities(authorities)
                    .build();
        } catch (AuthenticationException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
