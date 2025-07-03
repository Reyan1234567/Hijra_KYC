package com.example.hijra_kyc.dto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class BackReasonOutDto {
    private int id;
    private String comment;
    private String bankAccount;
    private String commentedBy;
    private LocalDateTime commentedAt;
}
