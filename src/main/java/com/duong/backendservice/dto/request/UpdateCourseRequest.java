package com.duong.backendservice.dto.request;

import com.duong.backendservice.common.CourseStatus;

import java.math.BigDecimal;

public record UpdateCourseRequest(
        String name,
        String description,
        BigDecimal price,
        Double hours,
        String thumbnailUrl,
        CourseStatus status
) {
}
