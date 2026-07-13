package com.duong.backendservice.mapper;

import com.duong.backendservice.dto.request.CreateCourseRequest;
import com.duong.backendservice.dto.request.UpdateCourseRequest;
import com.duong.backendservice.dto.response.CourseDetailResponse;
import com.duong.backendservice.dto.response.CreateCourseResponse;
import com.duong.backendservice.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {
    Course toCourse(CreateCourseRequest request);

    CreateCourseResponse toCreateCourseResponse(Course course);

    CourseDetailResponse toCourseDetailResponse(Course course);

    void updateCourse(UpdateCourseRequest request, @MappingTarget Course course);
}
