package com.duong.backendservice.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ChapterDetailResponse(
        String id,
        String chapterName,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
