package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.TokenType;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.repository.TokenRepository;
import com.duong.backendservice.service.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final TokenRepository tokenRepository;

    @Override
    public String generateAccessToken(String userId, Set<String> authorities) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(15, ChronoUnit.MINUTES));
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .claim("type", TokenType.ACCESS.name())
                .claim("authorities", authorities)
                .jwtID(UUID.randomUUID().toString())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateRefreshToken(String userId) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(14, ChronoUnit.DAYS));

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .claim("type", TokenType.REFRESH.name())
                .jwtID(UUID.randomUUID().toString())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SignedJWT validateToken(String token, TokenType tokenType) throws ParseException, JOSEException {
        if(!StringUtils.hasText(token)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean isValid = signedJWT.verify(new MACVerifier(secretKey));
        if(!isValid){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        TokenType type = TokenType.valueOf(signedJWT.getJWTClaimsSet().getClaimAsString("type"));
        if(type != tokenType){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(expirationTime.before(new Date())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String jwtID = signedJWT.getJWTClaimsSet().getJWTID();
        if(type == TokenType.REFRESH && tokenRepository.existsById(jwtID)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return signedJWT;
    }

    @Override
    public Set<String> getAuthorities(Object authoritiesClaim) {
        if(authoritiesClaim == null){
            return Set.of();
        }

        if(authoritiesClaim instanceof Set<?> authorities){
            return authorities.stream().map(String::valueOf)
                    .collect(Collectors.toSet());
        }

        return Set.of();
    }
}
