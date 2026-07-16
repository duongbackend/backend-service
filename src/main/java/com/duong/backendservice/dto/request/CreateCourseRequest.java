package com.duong.backendservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateCourseRequest(
        @NotBlank(message = "Course name is required")
        String name,

        @NotBlank(message = "Course description is required")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
        BigDecimal price,

        @Min(value = 0, message = "Hours must be greater than or equal to 0")
        Double hours,

        String thumbnailUrl
) {
}
