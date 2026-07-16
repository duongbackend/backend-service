package com.duong.backendservice.mapper;

import com.duong.backendservice.dto.request.CreateLessonRequest;
import com.duong.backendservice.dto.request.UpdateLessonRequest;
import com.duong.backendservice.dto.response.CreateLessonResponse;
import com.duong.backendservice.dto.response.LessonDetailResponse;
import com.duong.backendservice.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonMapper {
    @Mapping(target = "chapter", ignore = true)
    Lesson toLesson(CreateLessonRequest request);

    CreateLessonResponse toCreateLessonResponse(Lesson lesson);

    LessonDetailResponse toLessonDetailResponse(Lesson lesson);

    @Mapping(target = "chapter", ignore = true)
    void updateLesson(UpdateLessonRequest request, @MappingTarget Lesson lesson);
}
