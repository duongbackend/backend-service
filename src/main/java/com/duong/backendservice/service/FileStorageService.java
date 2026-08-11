package com.duong.backendservice.service;

import com.duong.backendservice.dto.response.FileResponse;
import com.duong.backendservice.dto.response.PresignerUrlResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    FileResponse uploadFile(MultipartFile file) throws IOException;

    PresignerUrlResponse getPresignerUrl(String fileName);

    String resolveUrl(String key);
}
