package com.duong.backendservice.dto.response;

import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        String mimeType,
        long size,
        String key
) {
}
