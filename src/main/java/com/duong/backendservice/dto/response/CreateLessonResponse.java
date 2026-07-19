package com.duong.backendservice.dto.response;

import com.duong.backendservice.common.LessonFormat;
import lombok.Builder;

import java.time.Instant;

@Builder
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
