package com.example.hijra_kyc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.service.BackReasonService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;

@RestController
@RequestMapping("/api/back-reasons")
@Validated
@RequiredArgsConstructor
public class BackReasonController {
    private final BackReasonService backReasonService;
    private final BackReasonMapper mapper;

    @PostMapping("/post-Back-Reason")
    public ResponseEntity<BackReasonOutDto> postBackReason(@RequestBody BackReasonInDto dto) {
        BackReasonOutDto result = backReasonService.createBackReason(dto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get-all-back-reasons")
    public ResponseEntity<List<BackReasonOutDto>> getAllBackReason() {
        List<BackReasonOutDto> result = backReasonService.getAllBackReasons();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search-back-reason/{id}")
    public ResponseEntity<BackReasonOutDto> getBackReasonById(@PathVariable Long id) {
        BackReason reason = backReasonService.searchBackReasonById(id);
        BackReasonOutDto dto = mapper.toDto(reason);
        return ResponseEntity.ok(dto);
    }
}
