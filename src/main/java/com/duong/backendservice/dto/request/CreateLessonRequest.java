package com.duong.backendservice.dto.request;

import com.duong.backendservice.common.LessonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreateLessonRequest(
        @NotBlank(message = "Chapter ID is required")
        String chapterId,

        @NotBlank(message = "Lesson name is required")
        @Size(max = 255, message = "Lesson name must not exceed 255 characters")
        String lessonName,

        String description,

        @NotNull(message = "Lesson format is required")
        LessonFormat lessonFormat,

        @URL(message = "Video URL is invalid")
        String videoUrl,

        @NotNull(message = "Free preview value is required")
        Boolean isFreePreview
) {
}
