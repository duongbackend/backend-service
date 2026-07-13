package com.duong.backendservice.service;

import com.duong.backendservice.dto.request.CreateCourseRequest;
import com.duong.backendservice.dto.request.UpdateCourseRequest;
import com.duong.backendservice.dto.response.CourseDetailResponse;
import com.duong.backendservice.dto.response.CreateCourseResponse;
import com.duong.backendservice.dto.response.PageResponse;

import java.math.BigDecimal;

public interface CourseService {
    CreateCourseResponse createCourse(CreateCourseRequest request);

    CourseDetailResponse getCourseBySlug(String slug);

    void deleteCourse(String id);

    CourseDetailResponse updateCourse(String id, UpdateCourseRequest request);

    PageResponse<CourseDetailResponse> getCourses(int page, int size, String name, BigDecimal from, BigDecimal to);
}
