package com.duong.backendservice.security;

import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(System.currentTimeMillis())
                .status(errorCode.getCode())
                .error(errorCode.getHttpStatus().getReasonPhrase())
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .build();

        JsonMapper jsonMapper = new JsonMapper();
        response.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
        response.flushBuffer();

    }
}
