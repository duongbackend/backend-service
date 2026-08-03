package com.duong.backendservice.service;

import com.duong.backendservice.common.TokenType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Set;

public interface JwtService {
    String generateAccessToken(String userId, Set<String> authorities);

    String generateRefreshToken(String userId);

    SignedJWT validateToken(String token, TokenType tokenType) throws ParseException, JOSEException;

    Set<String> getAuthorities(Object authoritiesClaim);
}
