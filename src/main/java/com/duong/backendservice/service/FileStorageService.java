package com.duong.backendservice.service;

import com.duong.backendservice.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    FileResponse uploadFile(MultipartFile file) throws IOException;
}
