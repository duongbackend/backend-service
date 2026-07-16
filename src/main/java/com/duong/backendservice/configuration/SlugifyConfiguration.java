package com.duong.backendservice.configuration;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifyConfiguration {
    @Bean
    Slugify slugify(){
        return Slugify.builder()
                .underscoreSeparator(false)
                .customReplacement("đ", "d")
                .customReplacement("Đ", "D")
                .customReplacement(" ", "-")
                .customReplacement("&", "and")
                .build();
    }
}
