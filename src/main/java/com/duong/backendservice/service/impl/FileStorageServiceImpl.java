package com.duong.backendservice.service.impl;

import com.duong.backendservice.dto.response.FileResponse;
import com.duong.backendservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "FILE-SERVICE")
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public FileResponse uploadFile(MultipartFile file) throws IOException {
        String key = generateKey(file.getOriginalFilename());
        PutObjectResponse response = s3Client.putObject(
                builder -> builder
                        .bucket(bucketName)
                        .contentType(file.getContentType())
                        .key(key),
                RequestBody.fromBytes(file.getBytes())
        );

        if(!response.sdkHttpResponse().isSuccessful()){
            log.error("Failed to upload file to S3");
        }

        return FileResponse.builder()
                .name(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .size(file.getSize())
                .key(key)
                .build();
    }

    private String generateKey(String fileName){
        String subfix = UUID.randomUUID().toString().replace("-", "");
        if(!StringUtils.hasText(fileName)){
            return subfix;
        }

        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(fileName, 0, fileName.lastIndexOf("."))
                .append("_")
                .append(subfix, 0, 10)
                .append(".")
                .append(fileName.substring(fileName.lastIndexOf(".") + 1))
                .toString();
    }
}
