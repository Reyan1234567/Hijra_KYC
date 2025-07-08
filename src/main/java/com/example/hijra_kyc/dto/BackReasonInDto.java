package com.example.hijra_kyc.dto;

import lombok.Data;

@Data
public class BackReasonInDto {
    private String comment;
    private String bankAccount;
    private int commentedBy;
}
