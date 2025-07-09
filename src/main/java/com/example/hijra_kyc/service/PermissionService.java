package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.PermissionDto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.mapper.PermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionOutDto createPermission(PermissionInDto dto) {
        Permission permission = permissionMapper.toEntity(dto);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toDto(savedPermission);
    }

    public List<PermissionOutDto> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public PermissionOutDto getPermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElse(null);
        return permission != null ? permissionMapper.toDto(permission) : null;
    }
}
