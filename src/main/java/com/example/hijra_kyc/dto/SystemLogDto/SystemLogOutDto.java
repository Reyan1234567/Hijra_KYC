package com.example.hijra_kyc.dto.SystemLogDto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SystemLogOutDto {
    private Long id;
    private Long userId;
    private LocalDateTime actionTime;
    private String actionType;
}
