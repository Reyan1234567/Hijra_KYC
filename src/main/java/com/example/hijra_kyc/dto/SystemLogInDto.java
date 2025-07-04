package com.example.hijra_kyc.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SystemLogInDto {
    private String actionType;
    private LocalDateTime actionTime;
    private int  userId;
}
