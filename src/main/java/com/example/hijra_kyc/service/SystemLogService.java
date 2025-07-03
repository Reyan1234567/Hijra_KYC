package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.repository.SystemLogRepository;
import com.example.hijra_kyc.dto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogOutDto;
import com.example.hijra_kyc.mapper.SystemLogMapper;
import com.example.hijra_kyc.model.SystemLog;

@Service
public class SystemLogService {
    private final SystemLogRepository logRepository;
    public SystemLogService(SystemLogRepository logRepository){
        this.logRepository = logRepository;
    }

    public SystemLogOutDto createLog(SystemLogInDto dto){
        SystemLog log = SystemLogMapper.toEntity(dto);
        SystemLog savedLog = logRepository.save(log);
        return SystemLogMapper.toDto(savedLog);
    }
    public List<SystemLogOutDto> getAllLogs(){
        return logRepository.findAll().stream()
            .map(SystemLogMapper::toDto)
            .collect(Collectors.toList());
    }
}
