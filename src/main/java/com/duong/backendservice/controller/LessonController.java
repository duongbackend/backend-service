package com.duong.backendservice.controller;

import com.duong.backendservice.dto.request.CreateLessonRequest;
import com.duong.backendservice.dto.request.UpdateLessonRequest;
import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.LessonDetailResponse;
import com.duong.backendservice.dto.response.CreateLessonResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    ApiResponse<CreateLessonResponse> createLesson(@RequestBody @Valid CreateLessonRequest request){
        CreateLessonResponse data = lessonService.createLesson(request);
        return ApiResponse.<CreateLessonResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LessonDetailResponse> updateLesson(@PathVariable String id, @RequestBody @Valid UpdateLessonRequest request){
        LessonDetailResponse data = lessonService.updateLesson(id, request);
        return ApiResponse.<LessonDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<LessonDetailResponse> getLessonById(@PathVariable String id){
        LessonDetailResponse data = lessonService.getLessonById(id);
        return ApiResponse.<LessonDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteLesson(@PathVariable String id){
        lessonService.deleteLesson(id);
        return ApiResponse.<Void>builder()
                .status("success")
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<LessonDetailResponse>> getLessons(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String lessonName
    ){
        PageResponse<LessonDetailResponse> data = lessonService.getLessons(page, size, lessonName);
        return ApiResponse.<PageResponse<LessonDetailResponse>>builder()
                .status("success")
                .data(data)
                .build();
    }
}
