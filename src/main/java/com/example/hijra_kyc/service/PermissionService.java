package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.PermissionDto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.mapper.PermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    // Create and save a new permission
    public PermissionOutDto createPermission(PermissionInDto dto) {
        Permission permission = permissionMapper.toModel(dto);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toOutDto(saved);
    }

    // Get all permissions
    public List<PermissionOutDto> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toOutDto)
                .collect(Collectors.toList());
    }
}
