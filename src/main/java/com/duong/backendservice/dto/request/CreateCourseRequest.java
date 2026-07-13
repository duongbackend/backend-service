package com.duong.backendservice.dto.request;

import java.math.BigDecimal;

public record CreateCourseRequest(
        String name,
        String description,
        BigDecimal price,
        Double hours,
        String thumbnailUrl
) {
}
