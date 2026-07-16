package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CreateChapterRequest;
import com.duong.backendservice.dto.request.UpdateChapterRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.ChapterDetailResponse;
import com.duong.backendservice.dto.response.CreateChapterResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;

    @PostMapping
    ApiResponse<CreateChapterResponse> createCourse(@RequestBody @Valid CreateChapterRequest request){
        CreateChapterResponse data = chapterService.createChapter(request);
        return ApiResponse.<CreateChapterResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ChapterDetailResponse> updateCourse(@PathVariable String id, @RequestBody UpdateChapterRequest request){
        ChapterDetailResponse data = chapterService.updateChapter(id, request);
        return ApiResponse.<ChapterDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @GetMapping("/{slug}")
    ApiResponse<ChapterDetailResponse> getCourseBySlug(@PathVariable String slug){
        ChapterDetailResponse data = chapterService.getChapterBySlug(slug);
        return ApiResponse.<ChapterDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @DeleteMapping("/{slug}")
    ApiResponse<Void> deleteCourse(@PathVariable String slug){
        chapterService.deleteChapter(slug);
        return ApiResponse.<Void>builder()
                .status("success")
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<ChapterDetailResponse>> getChapters(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String chapterName
    ){
        PageResponse<ChapterDetailResponse> data = chapterService.getChapters(page, size, chapterName);
        return ApiResponse.<PageResponse<ChapterDetailResponse>>builder()
                .status("success")
                .data(data)
                .build();
    }
}
