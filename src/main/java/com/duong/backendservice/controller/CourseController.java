package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CourseSearchRequest;
import com.duong.backendservice.dto.request.CreateCourseRequest;
import com.duong.backendservice.dto.request.UpdateCourseRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.CourseDetailResponse;
import com.duong.backendservice.dto.response.CreateCourseResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    ApiResponse<CreateCourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        CreateCourseResponse data = courseService.createCourse(request);

        return ApiResponse.<CreateCourseResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @GetMapping("/{slug}")
    ApiResponse<CourseDetailResponse> getCourseBySlug(@PathVariable String slug) {
        CourseDetailResponse data = courseService.getCourseBySlug(slug);
        return ApiResponse.<CourseDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        return ApiResponse.<Void>builder()
                .status("success")
                .message("Course deleted successfully")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CourseDetailResponse> updateCourse(@PathVariable String id, @RequestBody UpdateCourseRequest request){
        CourseDetailResponse data = courseService.updateCourse(id, request);
        return ApiResponse.<CourseDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<CourseDetailResponse>> getCourses(CourseSearchRequest searchRequest) {
        PageResponse<CourseDetailResponse> data = courseService.getCourses(searchRequest);
        return ApiResponse.<PageResponse<CourseDetailResponse>>builder()
                .status("success")
                .data(data)
                .build();
    }
}
