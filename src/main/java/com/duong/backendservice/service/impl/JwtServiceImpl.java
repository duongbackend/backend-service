package com.duong.backendservice.service.impl;

import com.duong.backendservice.entity.User;
import com.duong.backendservice.service.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(15, ChronoUnit.MINUTES));
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
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
    public String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(14, ChronoUnit.DAYS));

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
