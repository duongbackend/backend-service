package com.duong.backendservice.service;

import com.duong.backendservice.dto.request.CreateChapterRequest;
import com.duong.backendservice.dto.request.UpdateChapterRequest;
import com.duong.backendservice.dto.response.ChapterDetailResponse;
import com.duong.backendservice.dto.response.CreateChapterResponse;
import com.duong.backendservice.dto.response.PageResponse;

public interface ChapterService {
    CreateChapterResponse createChapter(CreateChapterRequest request);

    ChapterDetailResponse updateChapter(String id, UpdateChapterRequest request);

    ChapterDetailResponse getChapterBySlug(String slug);

    void deleteChapter(String id);

    PageResponse<ChapterDetailResponse> getChapters(int page, int size, String chapterName);
}
