package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.RoleName;
import com.duong.backendservice.common.TokenType;
import com.duong.backendservice.common.UserStatus;
import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.request.LoginRequest;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.dto.response.LoginResponse;
import com.duong.backendservice.entity.Role;
import com.duong.backendservice.entity.Token;
import com.duong.backendservice.entity.User;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.mapper.UserMapper;
import com.duong.backendservice.repository.TokenRepository;
import com.duong.backendservice.repository.UserRepository;
import com.duong.backendservice.service.AuthService;
import com.duong.backendservice.service.JwtService;
import com.duong.backendservice.service.MailService;
import com.duong.backendservice.service.RoleService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private final TokenRepository tokenRepository;
    private final RoleService roleService;
    private final MailService mailService;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public CreateUserResponse register(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserStatus(UserStatus.ACTIVE);

        Role defaultRole = roleService.getOrCreateRole(RoleName.USER);
        user.addRole(defaultRole);

        userRepository.save(user);
        mailService.sendEmail(user.getEmail(), user.getName(), "Welcome " + user.getName() + " to E-Learning Platform", "welcome");

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

            Set<String> authorities = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            String accessToken = jwtService.generateAccessToken(user.getId(), authorities);
            String refreshToken = jwtService.generateRefreshToken(user.getId());

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

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        try {
            SignedJWT signedJWT = jwtService.validateToken(refreshToken, TokenType.REFRESH);
            String userId = signedJWT.getJWTClaimsSet().getSubject();
            Set<String> authorities = jwtService.getAuthorities(signedJWT.getJWTClaimsSet().getClaim("authorities"));
            String accessToken = jwtService.generateAccessToken(userId, authorities);

            return LoginResponse.builder()
                    .userId(userId)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .authorities(Set.of())
                    .build();
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    public void logout(String refreshToken) {
        try {
            SignedJWT signedJWT = jwtService.validateToken(refreshToken, TokenType.REFRESH);
            String jwtID = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            long secondsTtl = (expirationTime.getTime() - new Date().getTime()) / 1000;
            Token token = Token.builder()
                    .jwtID(jwtID)
                    .secondsTtl(secondsTtl)
                    .build();

            tokenRepository.save(token);

            log.info("Logout success with jwtID: {}", jwtID);
        } catch (ParseException | JOSEException e) {
            log.error("Error while logout: {}", e.getMessage());
        }
    }
}
