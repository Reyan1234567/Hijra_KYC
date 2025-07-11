package com.example.hijra_kyc.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.BackReasonDto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonDto.BackReasonOutDto;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.Branch;
import com.example.hijra_kyc.model.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BackReasonMapper {
    public BackReason toEntity(BackReasonInDto dto,UserProfile commentedBy,UserProfile makerId,Branch branch){
        return BackReason.builder()
        .comment(dto.getComment())
        .bankAccount(dto.getBankAccount())
        .commentedBy(commentedBy)
        .makerId(makerId)
        .branchId(branch)
        .commentedAt(LocalDateTime.now())
        .build();
    }
    public BackReasonOutDto toDto(BackReason reason){
        BackReasonOutDto dto = new BackReasonOutDto();
        dto.setId(reason.getId());
        dto.setComment(reason.getComment());
        dto.setBankAccount(reason.getBankAccount());
        dto.setCommentedBy(reason.getCommentedBy().getId());
        dto.setMakerId(reason.getMakerId().getId());
        dto.setBranchId(reason.getBranchId().getBranchId());
        dto.setCommentedAt(reason.getCommentedAt());
        return dto;
    }
}
