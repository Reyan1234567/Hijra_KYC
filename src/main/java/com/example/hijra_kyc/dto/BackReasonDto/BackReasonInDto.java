package com.example.hijra_kyc.dto.BackReasonDto;

import lombok.Data;

@Data
public class BackReasonInDto {
    private String comment;
    private String bankAccount;
    private Long commentedBy;
    private Long makerId;
    private Long branchId;
}
