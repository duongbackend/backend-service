package com.duong.backendservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfiguration {
    @Value("${aws.region}")
    private String region;

    @Value("${aws.credentials.access-key}")
    private String accessKey;

    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    public S3Client s3Client(){
        Region awsRegion = Region.of(region);
        S3ClientBuilder builder = S3Client.builder();
        if(accessKey == null || secretKey == null){
            return builder.region(awsRegion)
                    .credentialsProvider(DefaultCredentialsProvider.builder().build())
                    .build();
        }

        return builder.region(awsRegion)
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();
    }

    @Bean
    S3Presigner s3Presigner(){
        Region awsRegion = Region.of(region);
        S3Presigner.Builder builder = S3Presigner.builder();
        if(accessKey == null || secretKey == null){
            return builder.region(awsRegion)
                    .credentialsProvider(DefaultCredentialsProvider.builder().build())
                    .build();
        }

        return builder.region(awsRegion)
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
                .build();
    }
}
