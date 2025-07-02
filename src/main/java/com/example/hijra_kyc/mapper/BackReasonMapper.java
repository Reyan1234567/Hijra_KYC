package com.example.hijra_kyc.mapper;

import java.time.LocalDateTime;

import com.example.hijra_kyc.dto.BackReasonInDto;
import com.example.hijra_kyc.dto.BackReasonOutDto;
import com.example.hijra_kyc.model.BackReason;

public class BackReasonMapper {
    public static BackReason toEntity(BackReasonInDto dto){
        return BackReason.builder()
        .comment(dto.getComment())
        .bankAccount(dto.getBankAccount())
        .commentedBy(dto.getCommentedBy())
        .commentedAt(LocalDateTime.now())
        .build();
    }
    public static BackReasonOutDto toDto(BackReason entity){
        BackReasonOutDto dto = new BackReasonOutDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setBankAccount(entity.getBankAccount());
        dto.setCommentedBy(entity.getCommentedBy());
        dto.setCommentedAt(entity.getCommentedAt());
        return dto;
    }
}
