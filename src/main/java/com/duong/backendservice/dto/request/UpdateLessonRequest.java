package com.duong.backendservice.dto.request;

import com.duong.backendservice.common.LessonFormat;
import com.duong.backendservice.common.LessonStatus;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UpdateLessonRequest(
        String chapterId,

        @Size(min = 1, max = 255, message = "Lesson name must be between 1 and 255 characters")
        String lessonName,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        LessonFormat lessonFormat,

        @URL(message = "Video URL is invalid")
        String videoUrl,

        Boolean isFreePreview,

        LessonStatus status
) {
}
