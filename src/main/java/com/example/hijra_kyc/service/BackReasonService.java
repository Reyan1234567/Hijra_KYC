package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BackReasonRepository;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BackReasonService {
    private final BackReasonRepository backReasonRepository;
    private final UserProfileRepository userRepo;
    private final BranchRepository branchRepo;
    private final BackReasonMapper mapper;

    public BackReasonOutDto createBackReason(BackReasonInDto dto){
        UserProfile commentedBy = userRepo.findById(dto.getCommentedBy())
         .orElseThrow(() -> new RuntimeException("User not found"));
          UserProfile maker = userRepo.findById(dto.getCommentedBy())
         .orElseThrow(() -> new RuntimeException("User not found"));
        Branch branch = branchRepo.findById(dto.getBranchId())
         .orElseThrow(() -> new RuntimeException("Branch not found"));
        BackReason backReason = mapper.toEntity(dto,commentedBy,maker,branch);
        BackReason savedBackReason = backReasonRepository.save(backReason);
        return mapper.toDto(savedBackReason);

    }
    public List<BackReasonOutDto> getAllBackReasons(){
        return backReasonRepository.findAll().stream()
                .map(mapper:: toDto)
                .collect(Collectors.toList());

    }
    public BackReason searchBackReasonById(Long id){
        return backReasonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Back Reason not found with id: " + id));
    }
}
