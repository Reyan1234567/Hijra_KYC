package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.dto.RoleDto.RoleInDto;
import com.example.hijra_kyc.dto.RoleDto.RoleOutDto;
import com.example.hijra_kyc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @PostMapping
    public ResponseEntity<RoleOutDto> createRole(@RequestBody RoleInDto dto) {
        RoleOutDto created = roleService.createRole(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<RoleOutDto>> getAllRoles() {
        List<RoleOutDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

   
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleOutDto> getRole(@PathVariable long roleId) {
        RoleOutDto role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }
    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<
            Set<PermissionOutDto>> getRolePermissions(@PathVariable long roleId) {
        Set<PermissionOutDto> permissions = roleService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

}
