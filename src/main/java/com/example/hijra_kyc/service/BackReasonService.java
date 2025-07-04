package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.repository.BackReasonRepository;

@Service
public class BackReasonService {
    private final BackReasonRepository backReasonRepository;

    public BackReasonService(BackReasonRepository backReasonRepository){
        this.backReasonRepository = backReasonRepository;
    }

    public BackReasonOutDto createBackReason(BackReasonInDto dto){
        BackReason backReason = BackReasonMapper.toEntity(dto);
        BackReason savedBackReason = backReasonRepository.save(backReason);
        return BackReasonMapper.toDto(savedBackReason);

    }
    public List<BackReasonOutDto> getAllBackReasons(){
        return backReasonRepository.findAll().stream()
                .map(BackReasonMapper:: toDto)
                .collect(Collectors.toList());

    }
}
