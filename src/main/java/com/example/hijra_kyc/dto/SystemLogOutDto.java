package com.example.hijra_kyc.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SystemLogOutDto {
    private int id;
    private String userId;
    private LocalDateTime actionTime;
    private String actionType;
}
