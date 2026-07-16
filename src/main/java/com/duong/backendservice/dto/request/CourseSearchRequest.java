package com.duong.backendservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CourseSearchRequest {
    private int page = 1;
    private int size = 10;
    private String name;
    private BigDecimal from = BigDecimal.ZERO;
    private BigDecimal to;
}
