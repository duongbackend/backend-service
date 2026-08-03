package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.RoleName;
import com.duong.backendservice.entity.Role;
import com.duong.backendservice.repository.RoleRepository;
import com.duong.backendservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ROLE-SERVICE")
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrCreateRole(RoleName roleName) {
        return roleRepository.findByName(roleName.name())
                .orElseGet(() -> {
                   Role role = Role.builder()
                           .name(roleName.name())
                           .build();
                   return roleRepository.save(role);
                });
    }
}