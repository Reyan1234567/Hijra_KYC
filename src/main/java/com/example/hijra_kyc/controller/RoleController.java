package com.example.hijra_kyc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.dto.RoleDto.RoleInDto;
import com.example.hijra_kyc.dto.RoleDto.RoleOutDto;
import com.example.hijra_kyc.service.RoleService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/post-role")
    public ResponseEntity<RoleOutDto> postRole(@RequestBody RoleInDto dto) {
        RoleOutDto result = roleService.createRole(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/" + "s")
    public ResponseEntity<List<RoleOutDto>> getAllRoles() {
        List<RoleOutDto> result = roleService.getAllRoles();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<List<PermissionOutDto>> getPermissionsByRole(@PathVariable Long roleId) {
        List<PermissionOutDto> permissions = roleService.getPermissionsByRole(roleId);
        return ResponseEntity.ok(permissions);
    }

}