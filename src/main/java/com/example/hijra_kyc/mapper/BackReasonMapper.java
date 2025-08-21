package com.example.hijra_kyc.mapper;

import java.time.Instant;
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
    public BackReason toEntity(BackReasonInDto inDto){
        return BackReason.builder()
        .comment(inDto.getComment())
        .rejectionTime(Instant.now())
        .build();
    }

    public BackReasonOutDto toDto(BackReason reason){
        return BackReasonOutDto.builder()
                .id(reason.getId())
                .comment(reason.getComment())
                .rejectionTime(reason.getRejectionTime())
                .makeId(reason.getMakeId().getId())
                .build();
    }
}
