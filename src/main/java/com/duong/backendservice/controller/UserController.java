package com.duong.backendservice.controller;

import com.duong.backendservice.dto.response.ApiResponse;
import com.duong.backendservice.dto.response.UserDetailResponse;
import com.duong.backendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    ApiResponse<List<UserDetailResponse>> getAllUsers(){
        var data = userService.getAllUsers();
        return ApiResponse.<List<UserDetailResponse>>builder()
                .status("success")
                .data(data)
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserDetailResponse> getUserById(@PathVariable String id){
        var data = userService.getUserById(id);
        return ApiResponse.<UserDetailResponse>builder()
                .status("success")
                .data(data)
                .build();
    }
}
