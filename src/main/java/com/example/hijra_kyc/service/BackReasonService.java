package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BackReasonRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

@Service
public class BackReasonService {
    private final BackReasonRepository backReasonRepository;
    private final UserProfileRepository userRepo;

    public BackReasonService(BackReasonRepository backReasonRepository,UserProfileRepository userRepo){
        this.backReasonRepository = backReasonRepository;
        this.userRepo = userRepo;
    }

    public BackReasonOutDto createBackReason(BackReasonInDto dto){
        UserProfile user = userRepo.findByUserId(dto.getCommentedBy())
    .orElseThrow(() -> new RuntimeException("User not found"));

        BackReason backReason = BackReasonMapper.toEntity(dto,user);
        BackReason savedBackReason = backReasonRepository.save(backReason);
        return BackReasonMapper.toDto(savedBackReason);

    }
    public List<BackReasonOutDto> getAllBackReasons(){
        return backReasonRepository.findAll().stream()
            .map(BackReasonMapper:: toDto)
            .collect(Collectors.toList());
        
    }
}
