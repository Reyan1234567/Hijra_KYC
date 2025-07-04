package com.example.hijra_kyc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.service.BackReasonService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;

@RestController
@RequestMapping("/api/back-reasons")
@Validated
public class BackReasonController {
    private final BackReasonService backReasonService;
    public BackReasonController(BackReasonService backReasonService){
        this.backReasonService = backReasonService;
    }
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
    public ResponseEntity<BackReasonOutDto> getBackReasonById(int id) {
        BackReason reason = backReasonService.searchBackReasonById(id);
        BackReasonOutDto dto = BackReasonMapper.toDto(reason);
        return ResponseEntity.ok(dto);
    }
}
