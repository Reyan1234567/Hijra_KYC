package com.example.hijra_kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.repository.MakeFormRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.mapper.BackReasonMapper;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.BackReasonRepository;
import com.example.hijra_kyc.repository.BranchRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class BackReasonService {
    private final BackReasonRepository backReasonRepository;
    private final MakeFormRepository makeFormRepository;
    private final BackReasonMapper backReasonMapper;
    private final UserProfileRepository userRepo;
    private final BranchRepository branchRepo;
    private final BackReasonMapper mapper;

    public BackReasonOutDto createBackReason(BackReasonInDto dto){
        BackReason backReason=mapper.toEntity(dto);
        MakeForm makeForm=makeFormRepository.findById(dto.getMakeFormId()).orElseThrow(()->new EntityNotFoundException("Make Form Not Found"));
        backReason.setMakeId(makeForm);
        return backReasonMapper.toDto(backReasonRepository.save(backReason));
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
