package com.example.hijra_kyc.mapper;
import java.time.LocalDateTime;

import com.example.hijra_kyc.dto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogOutDto;
import com.example.hijra_kyc.model.SystemLog;

public class SystemLogMapper {
    public static SystemLog toEntity(SystemLogInDto dto){
        return SystemLog.builder()
        .actionType(dto.getActionType())
        .actionTime(LocalDateTime.now())
        .build();
    }
    public static SystemLogOutDto toDto(SystemLog log){
        SystemLogOutDto dto = new SystemLogOutDto();
        dto.setId(log.getId());
        dto.setUserId(log.getUserId());
        dto.setActionTime(log.getActionTime());
        dto.setActionType(log.getActionType());
        return dto;
    }
}
