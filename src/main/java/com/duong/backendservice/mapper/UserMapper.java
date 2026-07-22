package com.duong.backendservice.mapper;

import com.duong.backendservice.dto.request.CreateUserRequest;
import com.duong.backendservice.dto.response.CreateUserResponse;
import com.duong.backendservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toUser(CreateUserRequest request);

    CreateUserResponse toCreateUserResponse(User user);
}
