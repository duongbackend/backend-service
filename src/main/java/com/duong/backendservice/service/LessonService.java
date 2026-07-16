package com.duong.backendservice.service;

import com.duong.backendservice.dto.request.CreateLessonRequest;
import com.duong.backendservice.dto.request.UpdateLessonRequest;
import com.duong.backendservice.dto.response.LessonDetailResponse;
import com.duong.backendservice.dto.response.CreateLessonResponse;
import com.duong.backendservice.dto.response.PageResponse;

public interface LessonService {
    CreateLessonResponse createLesson(CreateLessonRequest request);

    LessonDetailResponse updateLesson(String id, UpdateLessonRequest request);

    LessonDetailResponse getLessonById(String id);

    void deleteLesson(String id);

    PageResponse<LessonDetailResponse> getLessons(int page, int size, String lessonName);
}
