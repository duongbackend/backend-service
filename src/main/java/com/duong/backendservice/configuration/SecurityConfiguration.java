package com.duong.backendservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] GET_WHITELIST = {
            "/api/courses",
    };

    private static final String[] POST_WHITELIST = {
            "/api/users",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST,POST_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.GET,GET_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
