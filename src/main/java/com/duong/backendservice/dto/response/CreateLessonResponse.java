package com.duong.backendservice.dto.response;

import com.duong.backendservice.common.LessonFormat;

import java.time.Instant;

public record CreateLessonResponse(
        String id,
        String lessonName,
        String description,
        LessonFormat lessonFormat,
        String videoUrl,
        Boolean isFreePreview,
        Instant createdAt
) {
}
