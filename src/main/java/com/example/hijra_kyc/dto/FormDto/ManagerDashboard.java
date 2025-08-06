package com.example.hijra_kyc.dto.FormDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerDashboard {
    private int accepted;
    private int rejected;
    private int pending;
    private int total;
}