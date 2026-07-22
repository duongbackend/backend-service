package com.duong.backendservice.dto.response;

import com.duong.backendservice.common.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDetailResponse(
        String id,
        String email,
        String name,
        String phoneNumber,
        String avatarUrl,
        LocalDate birthDate,
        UserStatus userStatus
) {
}
