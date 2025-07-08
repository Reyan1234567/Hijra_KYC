package com.example.hijra_kyc.mapper;

import java.time.LocalDateTime;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.UserProfile;

public class BackReasonMapper {
    public static BackReason toEntity(BackReasonInDto dto,UserProfile user){
        return BackReason.builder()
        .comment(dto.getComment())
        .bankAccount(dto.getBankAccount())
        .commentedBy(user)
        .commentedAt(LocalDateTime.now())
        .build();
    }
    public static BackReasonOutDto toDto(BackReason reason){
        BackReasonOutDto dto = new BackReasonOutDto();
        dto.setId(reason.getId());
        dto.setComment(reason.getComment());
        dto.setBankAccount(reason.getBankAccount());
        dto.setCommentedBy(reason.getCommentedBy().getId());
        dto.setCommentedAt(reason.getCommentedAt());
        return dto;
    }
}
