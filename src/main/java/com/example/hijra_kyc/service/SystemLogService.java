package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogOutDto;
import com.example.hijra_kyc.mapper.SystemLogMapper;
import com.example.hijra_kyc.model.SystemLog;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.SystemLogRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

@Service
public class SystemLogService {
    private final SystemLogRepository logRepository;
    private final UserProfileRepository userRepo;
    public SystemLogService(SystemLogRepository logRepository,UserProfileRepository userRepo){
        this.logRepository = logRepository;
        this.userRepo = userRepo;
    }

    public SystemLogOutDto createLog(SystemLogInDto dto){
        UserProfile user = userRepo.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User Not found"));
        
        SystemLog log = SystemLogMapper.toEntity(dto,user);
        SystemLog savedLog = logRepository.save(log);
        return SystemLogMapper.toDto(savedLog);
    }
    public List<SystemLogOutDto> getAllLogs(){
        return logRepository.findAll().stream()
            .map(SystemLogMapper::toDto)
            .collect(Collectors.toList());
    }
}
