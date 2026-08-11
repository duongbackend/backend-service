package com.duong.backendservice.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class CreateCourseResponse {
    private String id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private Double hours;
    private String thumbnailUrl;
    private Instant createdAt;
}