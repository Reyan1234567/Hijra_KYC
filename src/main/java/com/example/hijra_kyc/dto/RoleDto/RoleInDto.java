package com.example.hijra_kyc.dto.RoleDto;

import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInDto {
    private String roleName;
    private Integer recordStatus;
//    private Set<PermissionOutDto> permissions;
    private Set<Long> permissionIds;

}

