package com.duong.backendservice.dto.response;

import lombok.Builder;

@Builder
public record PresignerUrlResponse(
        String url,
        String key
) {
}
