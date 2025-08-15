package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.SystemLogDto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogDto.SystemLogOutDto;
import com.example.hijra_kyc.mapper.SystemLogMapper;
import com.example.hijra_kyc.model.SystemLog;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.SystemLogRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemLogService {
    private final SystemLogRepository logRepository;
    private final UserProfileRepository userRepo;
    private final SystemLogMapper mapper;
    private final UserProfileService userService;

    public SystemLogOutDto createAuthLog(SystemLogInDto dto, String type, int flag){
        UserProfile user = userRepo.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User Not found"));

        userService.changeStatus(user.getId(), flag);
        userRepo.save(user);
        SystemLog log = mapper.toEntity(dto,user);
        log.setActionType(type);
        return mapper.toDto(logRepository.save(log));
    }

    public List<SystemLogOutDto> getAllLogs(){
        return logRepository.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
    public SystemLog searchLogById(Long id){
        return logRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Log not found with id: " + id));
    }

    public SystemLogOutDto createLogOutLog(SystemLogInDto dto) {
        int logoutFlag = 0;
        return createAuthLog(dto,"Logout", logoutFlag);
    }

    public SystemLogOutDto createLogInLog(SystemLogInDto dto) {
        int loginFlag = 1;
        return createAuthLog(dto,"LogIn", loginFlag);
    }
}
