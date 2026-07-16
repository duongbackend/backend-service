package com.duong.backendservice.dto.request;

import com.duong.backendservice.common.ChapterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateChapterRequest(
        String courseId,

        @NotBlank(message = "Chapter name is required")
        @Size(min = 1, max = 255, message = "Chapter name must be between 1 and 255 characters")
        String chapterName,

        String description,

        ChapterStatus status
) {
}
