package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.RoleInDto;
import com.example.hijra_kyc.dto.RoleOutDto;
import com.example.hijra_kyc.mapper.RoleMapper;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleOutDto createRole(RoleInDto dto) {
        Role role = RoleMapper.toEntity(dto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    public List<RoleOutDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleOutDto getRoleById(String roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        return role != null ? RoleMapper.toDto(role) : null;
    }
}
