package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CreateCourseRequest;
import com.duong.backendservice.dto.request.UpdateCourseRequest;
import com.duong.backendservice.dto.response.CourseDetailResponse;
import com.duong.backendservice.dto.response.CreateCourseResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    CreateCourseResponse createCourse(@RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping("/{slug}")
    CourseDetailResponse getCourseBySlug(@PathVariable String slug) {
        return courseService.getCourseBySlug(slug);
    }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
    }

    @PutMapping("/{id}")
    CourseDetailResponse updateCourse(@PathVariable String id, @RequestBody UpdateCourseRequest request){
        return courseService.updateCourse(id, request);
    }

    @GetMapping
    PageResponse<CourseDetailResponse> getCourses(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") BigDecimal from,
            @RequestParam(required = false) BigDecimal to
            ){
        return courseService.getCourses(page, size, name, from, to);
    }
}
