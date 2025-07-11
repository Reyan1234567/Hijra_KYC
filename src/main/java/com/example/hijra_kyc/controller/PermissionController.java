package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.PermissionDto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;


    @PostMapping("/post-permission")
    public ResponseEntity<PermissionOutDto> postPermission(@RequestBody PermissionInDto dto) {
        PermissionOutDto result = permissionService.createPermission(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-all-permissions")
    public ResponseEntity<List<PermissionOutDto>> getAllPermissions() {
        List<PermissionOutDto> result = permissionService.getAllPermissions();
        return ResponseEntity.ok(result);
    }
}
