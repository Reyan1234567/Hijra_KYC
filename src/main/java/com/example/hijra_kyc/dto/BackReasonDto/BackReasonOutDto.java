package com.example.hijra_kyc.dto.BackReasonDto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class BackReasonOutDto {
    private Long id;
    private String comment;
    private String bankAccount;
    private Long commentedBy;
    private Long makerId;
    private Long branchId;
    private LocalDateTime commentedAt;
}
