package com.example.hijra_kyc.mapper;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.SystemLogDto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogDto.SystemLogOutDto;
import com.example.hijra_kyc.model.SystemLog;
import com.example.hijra_kyc.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemLogMapper {
    public SystemLog toEntity(SystemLogInDto dto,UserProfile user){
        return SystemLog.builder()
        .actionTime(LocalDateTime.now())
        .userId(user)
        .build();
    }
    public SystemLogOutDto toDto(SystemLog log){
        SystemLogOutDto dto = new SystemLogOutDto();
        dto.setId(log.getId());
        dto.setUserId(log.getUserId().getId());
        dto.setActionTime(log.getActionTime());
        dto.setActionType(log.getActionType());
        return dto;
    }
}
