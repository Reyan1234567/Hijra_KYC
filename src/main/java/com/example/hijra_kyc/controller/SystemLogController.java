package com.example.hijra_kyc.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.SystemLogDto.SystemLogInDto;
import com.example.hijra_kyc.dto.SystemLogDto.SystemLogOutDto;
import com.example.hijra_kyc.mapper.SystemLogMapper;
import com.example.hijra_kyc.model.SystemLog;
import com.example.hijra_kyc.service.SystemLogService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/system-logs")
@RequiredArgsConstructor
@Validated
@RequiredArgsConstructor
public class SystemLogController {
    private final SystemLogService logService;
    private final SystemLogMapper mapper;

    @PostMapping("/add-new-logs")
    public ResponseEntity<SystemLogOutDto> postLogs(@RequestBody SystemLogInDto dto) {
       SystemLogOutDto result = logService.createLog(dto);
       return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-logs")
    public ResponseEntity<List<SystemLogOutDto>> getAllLogs() {
        List<SystemLogOutDto> result = logService.getAllLogs();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search-System-log/{id}")
    public ResponseEntity<SystemLogOutDto> getLogById(@PathVariable Long id) {
        SystemLog log = logService.searchLogById(id);
        SystemLogOutDto dto = mapper.toDto(log);
        return ResponseEntity.ok(dto);
    }
 
    
}
