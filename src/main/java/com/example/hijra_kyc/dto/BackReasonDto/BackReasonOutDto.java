package com.example.hijra_kyc.dto.BackReasonDto;

import java.time.Instant;
import java.time.LocalDateTime;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BackReasonOutDto {
    private Long id;
    private String comment;
    private Long makeId;
    private Instant rejectionTime;
}
