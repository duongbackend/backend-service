package com.duong.backendservice.service;

import com.duong.backendservice.common.TokenType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JwtService {
    String generateAccessToken(String userId);

    String generateRefreshToken(String userId);

    SignedJWT validateToken(String token, TokenType tokenType) throws ParseException, JOSEException;
}
