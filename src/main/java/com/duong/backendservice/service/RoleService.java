package com.duong.backendservice.service;

import com.duong.backendservice.common.RoleName;
import com.duong.backendservice.entity.Role;

public interface RoleService {
    Role getOrCreateRole(RoleName roleName);
}