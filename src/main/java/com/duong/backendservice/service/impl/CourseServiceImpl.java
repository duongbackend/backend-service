package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.CourseStatus;
import com.duong.backendservice.dto.request.CreateCourseRequest;
import com.duong.backendservice.dto.request.UpdateCourseRequest;
import com.duong.backendservice.dto.response.CourseDetailResponse;
import com.duong.backendservice.dto.response.CreateCourseResponse;
import com.duong.backendservice.entity.Course;
import com.duong.backendservice.mapper.CourseMapper;
import com.duong.backendservice.repository.CourseRepository;
import com.duong.backendservice.service.CourseService;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "COURSE-SERVICE")
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(request.name());
        if(courseRepository.existsBySlug(slug)){
            slug = slug + "-" + System.currentTimeMillis();
        }

        Course course = courseMapper.toCourse(request);
        course.setSlug(slug);
        course.setCreatedAt(Instant.now());
        course.setStatus(CourseStatus.ACTIVE);

        courseRepository.save(course);
        return courseMapper.toCreateCourseResponse(course);
    }

    @Override
    public CourseDetailResponse getCourseBySlug(String slug) {
        return courseRepository.findBySlug(slug)
                .map(courseMapper::toCourseDetailResponse)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setStatus(CourseStatus.INACTIVE);
        courseRepository.save(course);

        log.info("Course {} is deleted", course.getId());
    }

    @Override
    public CourseDetailResponse updateCourse(String id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if(course.getStatus() == CourseStatus.INACTIVE){
            throw new RuntimeException("Course is inactive");
        }

        if(StringUtils.hasText(request.name())){
            Slugify slugify = Slugify.builder().build();
            String slug = slugify.slugify(request.name());
            if(courseRepository.existsBySlug(slug)){
                slug = slug + "-" + System.currentTimeMillis();
            }
            course.setSlug(slug);
        }

        courseMapper.updateCourse(request, course);
        course.setUpdatedAt(Instant.now());
        courseRepository.save(course);

        return courseMapper.toCourseDetailResponse(course);
    }
}