package com.duong.backendservice.security;

import com.duong.backendservice.common.TokenType;
import com.duong.backendservice.exception.ErrorCode;
import org.jspecify.annotations.NonNull;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtTokenTypeValidator implements OAuth2TokenValidator<Jwt> {

    @Override
    public OAuth2TokenValidatorResult validate(@NonNull Jwt token) {
        TokenType tokenType = TokenType.valueOf(token.getClaimAsString("typ"));
        if(tokenType != TokenType.ACCESS){
            ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(errorCode.name(), errorCode.getMessage(), null));
        }

        return OAuth2TokenValidatorResult.success();
    }
}
