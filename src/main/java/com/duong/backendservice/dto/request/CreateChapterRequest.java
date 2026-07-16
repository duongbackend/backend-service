package com.duong.backendservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateChapterRequest(
        @NotBlank(message = "Course ID is required")
        String courseId,

        @NotBlank(message = "Chapter name is required")
        @Size(min = 1, max = 255, message = "Chapter name must be between 1 and 255 characters")
        String chapterName,

        String description
) {
}
