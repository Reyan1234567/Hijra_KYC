package com.example.hijra_kyc.mapper;

import java.time.LocalDateTime;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.model.BackReason;
import com.example.hijra_kyc.model.UserProfile;

public class BackReasonMapper {
    //UserProfile user = new UserProfile();
    public static BackReason toEntity(BackReasonInDto dto,UserProfile user){
        return BackReason.builder()
        .comment(dto.getComment())
        .bankAccount(dto.getBankAccount())
        .commentedBy(user)
        .commentedAt(LocalDateTime.now())
        .build();
    }
    public static BackReasonOutDto toDto(BackReason entity){
        BackReasonOutDto dto = new BackReasonOutDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setBankAccount(entity.getBankAccount());
        dto.setCommentedBy(entity.getCommentedBy().getUserId());
        dto.setCommentedAt(entity.getCommentedAt());
        return dto;
    }
}
