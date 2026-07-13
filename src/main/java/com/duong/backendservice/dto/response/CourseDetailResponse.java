package com.duong.backendservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record CourseDetailResponse(
        String id,
        String name,
        String slug,
        String description,
        BigDecimal price,
        Double hours,
        String thumbnailUrl,
        Instant createdAt,
        Instant updatedAt
) {
}