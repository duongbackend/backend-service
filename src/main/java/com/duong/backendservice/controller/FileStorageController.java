package com.duong.backendservice.controller;

import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.FileResponse;
import com.duong.backendservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    ApiResponse<FileResponse> uploadFile(@RequestParam MultipartFile file) throws IOException {
        FileResponse data = fileStorageService.uploadFile(file);
        return ApiResponse.<FileResponse>builder()
                .status("success")
                .data(data)
                .build();
    }
}
