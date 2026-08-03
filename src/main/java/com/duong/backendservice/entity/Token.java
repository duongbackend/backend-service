package com.duong.backendservice.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("redis_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    private String jwtID;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long secondsTtl;

    @NotBlank
    private String userId;
}
